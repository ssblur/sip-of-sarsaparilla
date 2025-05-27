package com.ssblur.sipofsarsaparilla.item.armor

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ArmorMaterial.Layer
import net.minecraft.world.item.crafting.Ingredient

object SipMaterials {
  val BANDANNA = SipOfSarsaparilla.registerArmorMaterial("bandanna") {
    ArmorMaterial(
      mapOf(
        ArmorItem.Type.HELMET to 2,
        ArmorItem.Type.CHESTPLATE to 2,
        ArmorItem.Type.LEGGINGS to 2,
        ArmorItem.Type.BOOTS to 2,
        ArmorItem.Type.BODY to 2,
      ),
      14,
      SoundEvents.ARMOR_EQUIP_LEATHER,
      { Ingredient.of(ItemTags.WOOL) },
      listOf(
        Layer(SipOfSarsaparilla.location("bandanna"), "", true),
        Layer(SipOfSarsaparilla.location("bandanna"), "_overlay", false),
      ),
      0f,
      0f,
    )
  }

  fun register() {

  }

  fun registerClient() {

  }
}