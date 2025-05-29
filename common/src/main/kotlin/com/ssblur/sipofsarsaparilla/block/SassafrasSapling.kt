package com.ssblur.sipofsarsaparilla.block

import com.ssblur.unfocused.extension.BlockExtension.renderType
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.FarmBlock
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class SassafrasSapling(properties: Properties) : Block(properties.randomTicks()), BonemealableBlock {
  companion object {
    val STAGE: IntegerProperty = BlockStateProperties.STAGE
    val SHAPE = box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0)
  }

  init {
    registerDefaultState(defaultBlockState().setValue(STAGE, 0))
    try { clientInit() } catch (_: NoSuchMethodError) {}
  }

  override fun getStateForPlacement(blockPlaceContext: BlockPlaceContext): BlockState? {
    val dirt = blockPlaceContext.level.getBlockState(blockPlaceContext.clickedPos.below())
    if(dirt.`is`(BlockTags.DIRT) || dirt.block is FarmBlock)
      return super.getStateForPlacement(blockPlaceContext)
    return null
  }

  @Environment(EnvType.CLIENT)
  private fun clientInit() {
    this.renderType(RenderType.cutout())
  }

  override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
    builder.add(STAGE)
  }

  override fun getShape(
    blockState: BlockState,
    blockGetter: BlockGetter,
    blockPos: BlockPos,
    collisionContext: CollisionContext
  ): VoxelShape = SHAPE

  override fun randomTick(
    blockState: BlockState,
    serverLevel: ServerLevel,
    blockPos: BlockPos,
    randomSource: RandomSource
  ) {
    super.randomTick(blockState, serverLevel, blockPos, randomSource)
    // Slightly more likely than standard saplings because this tree is small (:
    if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9 && randomSource.nextInt(5) == 0) {
      this.grow(serverLevel, blockPos, blockState)
    }
  }

  override fun isValidBonemealTarget(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState) = true

  override fun isBonemealSuccess(
    level: Level,
    randomSource: RandomSource,
    blockPos: BlockPos,
    blockState: BlockState
  ) = level.random.nextFloat() < 0.45

  override fun performBonemeal(
    serverLevel: ServerLevel,
    randomSource: RandomSource,
    blockPos: BlockPos,
    blockState: BlockState
  ) {
    grow(serverLevel, blockPos, blockState)
  }

  fun grow(level: Level, blockPos: BlockPos, blockState: BlockState) {
    val max = STAGE.possibleValues.max()
    val stage = blockState.getValue(STAGE)
    if(stage >= max) {
      growTree(level, blockPos)
    } else {
      level.setBlockAndUpdate(blockPos, blockState.setValue(STAGE, stage + 1))
    }
  }

  fun growTree(level: Level, blockPos: BlockPos) {
    val height = level.random.nextInt(2) + 2
    val log = SipBlocks.SASSAFRAS_LOG.first.get().defaultBlockState()
    for(i in 0..height) {
      if(
        level.getBlockState(blockPos.above(i)).`is`(BlockTags.REPLACEABLE_BY_TREES)
        || level.getBlockState(blockPos.above(i)).canBeReplaced()
        || i == 0
      )
        level.setBlockAndUpdate(blockPos.above(i), log)
    }
    level.setBlockAndUpdate(blockPos.above(height), SipBlocks.SASSAFRAS_LOG_BRANCHED.first.get().defaultBlockState())

    val leaves = SipBlocks.SASSAFRAS_LEAVES.first.get().defaultBlockState().setValue(LeavesBlock.DISTANCE, 1)
    for(pos in listOf(
      blockPos.above(height + 1),
      blockPos.above(height).north(),
      blockPos.above(height).south(),
      blockPos.above(height).east(),
      blockPos.above(height).west())
    )
      if(level.getBlockState(pos).`is`(BlockTags.REPLACEABLE_BY_TREES) || level.getBlockState(pos).canBeReplaced())
        level.setBlockAndUpdate(pos, leaves)
  }
}