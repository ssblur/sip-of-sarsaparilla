package com.ssblur.sipofsarsaparilla.item

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla
import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla.registerItem
import com.ssblur.sipofsarsaparilla.item.armor.CowboyHat
import com.ssblur.sipofsarsaparilla.item.armor.SipMaterials
import com.ssblur.sipofsarsaparilla.item.armor.TheDimmadome
import com.ssblur.sipofsarsaparilla.sound.SipSounds
import com.ssblur.unfocused.helper.ColorHelper
import com.ssblur.unfocused.helper.ColorHelper.registerColor
import com.ssblur.unfocused.tab.CreativeTabs.add
import com.ssblur.unfocused.tab.CreativeTabs.registerCreativeTab
import com.ssblur.unfocused.tab.CreativeTabs.tab
import net.minecraft.core.component.DataComponents
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.*
import net.minecraft.world.item.component.DyedItemColor

@Suppress("unused")
object SipItems {
  val TAB = SipOfSarsaparilla.registerCreativeTab("sip_of_sarsaparilla", "itemGroup.sipofsarsaparilla.tab") {
    SARSAPARILLA.get()
  }

  val COWBOY_HAT = registerItem("cowboy_hat") {
    CowboyHat(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, Item.Properties().durability(64))
  }.tab(TAB)
  val THE_DIMMADOME = registerItem("the_dimmadome") {
    TheDimmadome(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, Item.Properties().durability(64))
  }.tab(TAB)
  val BANDANNA = registerItem("bandanna") {
    ArmorItem(SipMaterials.BANDANNA, ArmorItem.Type.CHESTPLATE, Item.Properties().durability(48))
  }.tab(TAB)

  val SARSAPARILLA = registerItem("sarsaparilla") {
    object: Item(Properties()
      .food(
        FoodProperties
          .Builder()
          .alwaysEdible()
          .usingConvertsTo(Items.GLASS_BOTTLE)
          .nutrition(4)
          .effect(MobEffectInstance(MobEffects.LUCK, 3000), 1.0f)
          .build()
      )
    ) {
        override fun getUseAnimation(itemStack: ItemStack): UseAnim {
          return UseAnim.DRINK
        }
      }
  }

  val BULLET = registerItem("bullet") {
    Item(Item.Properties())
  }.tab(TAB)
  val POPGUN = registerItem("popgun") {
    Gun(1, 6, Item.Properties())
      .withSounds(SipSounds.GUN_CAP_POP.get())
  }.tab(TAB)
  val WILD_REVOLVER = registerItem("wild_revolver") {
    Gun(8, 6, Item.Properties())
      .withSounds(SipSounds.GUN_BLAST.get())
      .withAmmo(BULLET)
  }.tab(TAB)
  val GOLDEN_GUN = registerItem("golden_gun") {
    Gun(20, 6, Item.Properties())
      .withSounds(SipSounds.GUN_BLAST.get())
      .withAmmo(BULLET)
  }.tab(TAB)

  fun register() {
    SipMaterials.register()

    TAB.then{
      SARSAPARILLA.tab(TAB)
    }
    BANDANNA.then{
      ColorHelper.forEachColor{
        val item = ItemStack(BANDANNA)
        item[DataComponents.DYED_COLOR] = DyedItemColor(it.dyeColor.textureDiffuseColor, true)
        TAB.add(item)
      }
    }
  }

  fun registerClient() {
    SipMaterials.registerClient()

    COWBOY_HAT.then{
      it.registerColor{ itemStack, layer ->
        if (layer == 1) 0xff000000.toInt().or(getColor(itemStack, 0x3F1900)) else 0xffffffffu.toInt()
      }
    }

    BANDANNA.then{
      it.registerColor{ itemStack, layer ->
        if (layer == 1) 0xff000000.toInt().or(getColor(itemStack, 0x3F1900)) else 0xffffffffu.toInt()
      }
    }
  }

  fun getColor(itemStack: ItemStack, default: Int = 0x3F1900): Int {
    return itemStack[DataComponents.DYED_COLOR]?.rgb ?: default
  }
}