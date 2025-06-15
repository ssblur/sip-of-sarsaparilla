package com.ssblur.sipofsarsaparilla.damage

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla.location
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.entity.Entity

object SipDamage {
  val GUN: ResourceKey<DamageType> = ResourceKey.create(
    Registries.DAMAGE_TYPE,
    location("gun")
  )

  fun gun(entity: Entity): DamageSource {
    val level = entity.level()
    return level.registryAccess().registry(Registries.DAMAGE_TYPE).map {
      DamageSource(it.getHolderOrThrow(GUN), entity)
    }.orElseThrow()
  }
}