package com.ssblur.sipofsarsaparilla.item.armor

import com.ssblur.sipofsarsaparilla.item.armor.model.ArmorRenderer
import net.minecraft.client.model.HumanoidModel
import net.minecraft.core.Holder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.GeoRenderProvider
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.PlayState
import software.bernie.geckolib.renderer.GeoArmorRenderer
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer

class TheDimmadome(holder: Holder<ArmorMaterial>, type: Type, properties: Properties):
  ArmorItem(holder, type, properties), GeoItem {
  private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

  override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
    controllers.add(AnimationController(this, 20) { PlayState.CONTINUE })
  }

  override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
    return cache
  }

  override fun createGeoRenderer(consumer: Consumer<GeoRenderProvider>) {
    consumer.accept(object : GeoRenderProvider {
      private var renderer: GeoArmorRenderer<*>? = null
      override fun <T : LivingEntity?> getGeoArmorRenderer(
        livingEntity: T?,
        itemStack: ItemStack?,
        equipmentSlot: EquipmentSlot?,
        original: HumanoidModel<T>?
      ): HumanoidModel<*>? {
        if (this.renderer == null) this.renderer = ArmorRenderer("armor/the_dimmadome")
        return this.renderer
      }
    })
  }
}