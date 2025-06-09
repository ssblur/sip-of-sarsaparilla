package com.ssblur.sipofsarsaparilla.feature

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla
import com.ssblur.sipofsarsaparilla.block.SassafrasSapling
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

object SipFeatures {
  fun register() {
    SipOfSarsaparilla.registerFeature("sassafras_tree") {
      object: Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {
        override fun place(featurePlaceContext: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
          val level = featurePlaceContext.level()
          val pos = featurePlaceContext.origin()

          if(!level.getBlockState(pos.below()).`is`(BlockTags.DIRT)) return false

          SassafrasSapling.growTree(level, pos)
          return true
        }
      }
    }
  }
}