package com.ssblur.sipofsarsaparilla.item

import com.google.common.base.Predicate
import com.ssblur.sipofsarsaparilla.network.client.SipNetworkS2C
import com.ssblur.sipofsarsaparilla.sound.SipSounds
import com.ssblur.unfocused.registry.RegistrySupplier
import net.minecraft.core.component.DataComponents
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import kotlin.math.min
import kotlin.math.roundToInt

class Gun(val damage: Int, val ammo: Int, properties: Properties) : Item(properties) {
  var sounds: List<SoundEvent> = listOf()
  var ammoPredicate: Predicate<ItemStack>? = null

  fun withSounds(vararg sounds: SoundEvent): Gun {
    this.sounds = sounds.toList()
    return this
  }

  fun withAmmo(vararg item: RegistrySupplier<Item>): Gun {
    ammoPredicate = Predicate {
      it.item in item.map { it.get() }
    }
    return this
  }

  fun reload(level: Level, player: Player, item: ItemStack) {
    val ammo = item[DataComponents.DAMAGE] ?: 0
    if(ammoPredicate == null) {
      item[DataComponents.DAMAGE] = 6
    } else {
      val match = player.inventory.items.firstOrNull { ammoPredicate!!.test(it) }
      if(match == null) {
        player.cooldowns.addCooldown(this, 5)
        level.playSound(null, player.blockPosition().above(), SipSounds.NO_AMMO_CLICK.get(), SoundSource.PLAYERS)
        return
      }
      val available = min(6 - ammo, match.count)
      item[DataComponents.DAMAGE] = available + ammo
      match.shrink(available)
    }
    level.playSound(null, player.blockPosition().above(), SipSounds.GUN_RELOAD.get(), SoundSource.PLAYERS)
    player.cooldowns.addCooldown(this, 60)
  }

  override fun isBarVisible(itemStack: ItemStack): Boolean = true

  override fun getBarWidth(item: ItemStack): Int {
    if(item[DataComponents.DAMAGE] != null)
      return (13f * item[DataComponents.DAMAGE]!!/ammo).roundToInt()
    return 0
  }

  override fun use(
    level: Level,
    player: Player,
    interactionHand: InteractionHand
  ): InteractionResultHolder<ItemStack?>? {
    val item = player.getItemInHand(interactionHand)
    if(level.isClientSide) return InteractionResultHolder.sidedSuccess(item, true)

    val ammo = item[DataComponents.DAMAGE] ?: 0
    if(ammo <= 0 || player.isCrouching) {
      reload(level, player, item)
    } else {
      SipNetworkS2C.requestGunTrace(
        SipNetworkS2C.RequestGunTrace(interactionHand),
        listOf(player)
      )
      if(sounds.isNotEmpty())
        level.playSound(null, player.blockPosition().above(), sounds.random(), SoundSource.PLAYERS)

      item[DataComponents.DAMAGE] = ammo - 1
    }

    return InteractionResultHolder.success(item)
  }
}