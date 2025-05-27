package com.ssblur.sipofsarsaparilla.item.armor.model

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla
import com.ssblur.sipofsarsaparilla.item.SipItems
import com.ssblur.sipofsarsaparilla.item.armor.CowboyHat
import software.bernie.geckolib.cache.`object`.GeoBone
import software.bernie.geckolib.model.DefaultedItemGeoModel
import software.bernie.geckolib.renderer.specialty.DyeableGeoArmorRenderer
import software.bernie.geckolib.util.Color

class ArmorRenderer(path: String) : DyeableGeoArmorRenderer<CowboyHat>(
  DefaultedItemGeoModel(SipOfSarsaparilla.location(path))
) {
  override fun isBoneDyeable(bone: GeoBone?): Boolean {
    return bone?.name?.contains("dyeable") ?: false
  }

  override fun getColorForBone(bone: GeoBone?): Color {
    val color = SipItems.getColor(this.currentStack)
    return Color.ofOpaque(color)
  }
}