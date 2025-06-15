package com.ssblur.sipofsarsaparilla.network.server

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla.location
import com.ssblur.sipofsarsaparilla.damage.SipDamage
import com.ssblur.sipofsarsaparilla.item.Gun
import com.ssblur.unfocused.network.NetworkManager
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity

object SipNetworkC2S {
  data class GunTrace(val entityID: Int?, val hand: InteractionHand?)
  val gunTrace = NetworkManager.registerC2S(
    location("server_gun_trace"),
    GunTrace::class
  ) { (entityID, hand), player ->
    if(hand == null) return@registerC2S

    val item = player.getItemInHand(hand).item
    if(item !is Gun) return@registerC2S
    if(player.cooldowns.isOnCooldown(item)) return@registerC2S
    player.cooldowns.addCooldown(item, 15)

    if(entityID == null) return@registerC2S

    val level = player.level()
    val entity = level.getEntity(entityID)
    if(entity == null || entity.distanceTo(player) > 20) return@registerC2S
    if(entity is LivingEntity)
      entity.hurt(SipDamage.gun(entity), item.damage.toFloat())
  }

  fun register() {}
}