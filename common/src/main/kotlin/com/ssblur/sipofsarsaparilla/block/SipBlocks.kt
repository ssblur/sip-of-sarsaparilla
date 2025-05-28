package com.ssblur.sipofsarsaparilla.block

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla.registerBlockWithItem
import com.ssblur.sipofsarsaparilla.item.SipItems
import com.ssblur.unfocused.extension.BlockExtension.renderType
import com.ssblur.unfocused.tab.CreativeTabs.tab
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

object SipBlocks {
  val OAK_SALOON_DOOR = registerBlockWithItem("oak_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.OAK_DOOR)) }
  val SPRUCE_SALOON_DOOR = registerBlockWithItem("spruce_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.SPRUCE_DOOR)) }
  val BIRCH_SALOON_DOOR = registerBlockWithItem("birch_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.BIRCH_DOOR)) }
  val JUNGLE_SALOON_DOOR = registerBlockWithItem("jungle_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.JUNGLE_DOOR)) }
  val ACACIA_SALOON_DOOR = registerBlockWithItem("acacia_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val DARK_OAK_SALOON_DOOR = registerBlockWithItem("dark_oak_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.DARK_OAK_DOOR)) }
  val MANGROVE_SALOON_DOOR = registerBlockWithItem("mangrove_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.MANGROVE_DOOR)) }
  val CHERRY_SALOON_DOOR = registerBlockWithItem("cherry_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.CHERRY_DOOR)) }
  val CRIMSON_SALOON_DOOR = registerBlockWithItem("crimson_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.CRIMSON_DOOR)) }
  val WARPED_SALOON_DOOR = registerBlockWithItem("warped_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.WARPED_DOOR)) }
  val BAMBOO_SALOON_DOOR = registerBlockWithItem("bamboo_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.BAMBOO_DOOR)) }
  val PALE_SALOON_DOOR = registerBlockWithItem("pale_saloon_door"){
    SaloonDoor(ofFullCopy(Blocks.DARK_OAK_DOOR).mapColor(MapColor.COLOR_GRAY))
  }

  val SASSAFRAS_SAPLING = registerBlockWithItem("sassafras_sapling") {
    SassafrasSapling(ofFullCopy(Blocks.SPRUCE_SAPLING))
  }
  val SASSAFRAS_LOG = registerBlockWithItem("sassafras_log") {
    object: RotatedPillarBlock(ofFullCopy(Blocks.SPRUCE_LOG).noOcclusion()) {
      override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
      ): VoxelShape {
        if(blockState.getValue(AXIS) == Direction.Axis.Z)
          return box(6.0, 6.0, 0.0, 10.0, 10.0, 16.0)
        if(blockState.getValue(AXIS) == Direction.Axis.X)
          return box(0.0, 6.0, 6.0, 16.0, 10.0, 10.0)
        return box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0)
      }

      init {
        try { clientInit() } catch (_: NoSuchMethodError) {}
      }

      @Environment(EnvType.CLIENT)
      private fun clientInit() {
        this.renderType(RenderType.translucent())
      }
    }
  }
  val SASSAFRAS_LOG_BRANCHED = registerBlockWithItem("sassafras_log_branched") {
    object: Block(ofFullCopy(Blocks.SPRUCE_PLANKS).noOcclusion()) {
      override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
      ): VoxelShape {
        return box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0)
      }

      init {
        try { clientInit() } catch (_: NoSuchMethodError) {}
      }

      @Environment(EnvType.CLIENT)
      private fun clientInit() {
        this.renderType(RenderType.cutout())
      }
    }
  }
  val SASSAFRAS_LEAVES = registerBlockWithItem("sassafras_leaves") {
    LeavesBlock(ofFullCopy(Blocks.SPRUCE_LEAVES))
  }

  fun register() {
    OAK_SALOON_DOOR.second.tab(SipItems.TAB)
    SPRUCE_SALOON_DOOR.second.tab(SipItems.TAB)
    BIRCH_SALOON_DOOR.second.tab(SipItems.TAB)
    JUNGLE_SALOON_DOOR.second.tab(SipItems.TAB)
    ACACIA_SALOON_DOOR.second.tab(SipItems.TAB)
    DARK_OAK_SALOON_DOOR.second.tab(SipItems.TAB)
    MANGROVE_SALOON_DOOR.second.tab(SipItems.TAB)
    CHERRY_SALOON_DOOR.second.tab(SipItems.TAB)
    CRIMSON_SALOON_DOOR.second.tab(SipItems.TAB)
    WARPED_SALOON_DOOR.second.tab(SipItems.TAB)
    BAMBOO_SALOON_DOOR.second.tab(SipItems.TAB)
    PALE_SALOON_DOOR.second.tab(SipItems.TAB)

    SASSAFRAS_SAPLING.second.tab(SipItems.TAB)
    SASSAFRAS_LOG.second.tab(SipItems.TAB)
    SASSAFRAS_LOG_BRANCHED.second.tab(SipItems.TAB)
    SASSAFRAS_LEAVES.second.tab(SipItems.TAB)
  }

  fun registerClient() {

  }
}