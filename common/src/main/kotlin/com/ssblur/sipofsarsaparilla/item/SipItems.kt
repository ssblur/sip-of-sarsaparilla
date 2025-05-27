package com.ssblur.sipofsarsaparilla.item

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla
import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla.registerItem
import com.ssblur.unfocused.helper.ColorHelper.registerColor
import com.ssblur.unfocused.tab.CreativeTabs.registerCreativeTab
import com.ssblur.unfocused.tab.CreativeTabs.tab
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterials
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

@Suppress("unused")
object SipItems {
  val TAB = SipOfSarsaparilla.registerCreativeTab("sip_of_sarsaparilla", "itemGroup.sipofsarsaparilla.tab") {
    COWBOY_HAT.get()
  }

  val COWBOY_HAT = registerItem("cowboy_hat") {
    CowboyHat(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, Item.Properties())
  }
  val THE_DIMMADOME = registerItem("the_dimmadome") {
    TheDimmadome(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, Item.Properties())
  }.tab(TAB)
  val BANDANA = registerItem("bandana") {
    ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, Item.Properties())
  }.tab(TAB)

  fun register() {
    TAB.then{
      COWBOY_HAT.tab(TAB)
    }
  }

  fun registerClient() {
    COWBOY_HAT.then{
      it.registerColor{ itemStack, layer ->
        if (layer == 1) 0xff000000.toInt().or(getColor(itemStack)) else 0xffffffffu.toInt()
      }
    }
  }

  fun getColor(itemStack: ItemStack): Int {
    return itemStack[DataComponents.DYED_COLOR]?.rgb ?: 0x3F1900
  }
}