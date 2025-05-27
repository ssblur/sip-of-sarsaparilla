package com.ssblur.sipofsarsaparilla.block

import com.ssblur.unfocused.extension.BlockExtension.renderType
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import kotlin.math.abs

class SaloonDoor(properties: Properties) : TransparentBlock(properties.noOcclusion().noCollission()) {
  companion object {
    val NS_AABB_BOTTOM: VoxelShape = box(6.0, 4.0, 0.0, 10.0, 16.0, 16.0)
    val EW_AABB_BOTTOM: VoxelShape = box(0.0, 4.0, 6.0, 16.0, 16.0, 10.0)
    val NS_AABB_TOP: VoxelShape = box(6.0, 0.0, 0.0, 10.0, 12.0, 16.0)
    val EW_AABB_TOP: VoxelShape = box(0.0, 0.0, 6.0, 16.0, 12.0, 10.0)

    val HALF: BooleanProperty = BooleanProperty.create("half")
    val SWING: IntegerProperty = IntegerProperty.create("swing", 0, 2)
    val FACING: DirectionProperty =
      DirectionProperty.create("facing", Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
  }

  init {
    try { clientInit() } catch (_: NoSuchMethodError) {}
  }

  fun clientInit() {
    this.renderType(RenderType.cutout())
  }

  override fun tick(blockState: BlockState, level: ServerLevel, blockPos: BlockPos, randomSource: RandomSource) {
    super.tick(blockState, level, blockPos, randomSource)
    swingTick(level, blockPos, blockState)
  }

  override fun setPlacedBy(
    level: Level,
    blockPos: BlockPos,
    blockState: BlockState,
    livingEntity: LivingEntity?,
    itemStack: ItemStack
  ) {
    level.setBlock(blockPos.above(), blockState.setValue(HALF, true), 3)
    super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack)
  }

  override fun onPlace(blockState: BlockState, level: Level, blockPos: BlockPos, blockState2: BlockState, bl: Boolean) {
    super.onPlace(blockState, level, blockPos, blockState2, bl)
    if(!level.isClientSide && !blockState.getValue(HALF)) level.scheduleTick(blockPos, this, 1)
  }

  public override fun getShape(
    blockState: BlockState,
    blockGetter: BlockGetter,
    blockPos: BlockPos,
    collisionContext: CollisionContext
  ): VoxelShape {
    if (blockState.getValue(HALF)) return if (blockState.getValue(FACING).axis === Direction.Axis.Z) NS_AABB_TOP else EW_AABB_TOP
    return if (blockState.getValue(FACING).axis === Direction.Axis.Z) NS_AABB_BOTTOM else EW_AABB_BOTTOM
  }

  override fun getStateForPlacement(blockPlaceContext: BlockPlaceContext): BlockState? {
    val pos = blockPlaceContext.clickedPos
    val level = blockPlaceContext.level
    if (pos.y > level.maxBuildHeight - 1 || !level.getBlockState(pos.above())
        .canBeReplaced(blockPlaceContext)
    ) return null

    val dir = blockPlaceContext.clickedFace
    if (dir.axis === Direction.Axis.Y) return null

    var blockState = level.getBlockState(pos.offset(dir.opposite.normal))
    if (!blockState.isFaceSturdy(level, pos.offset(dir.opposite.normal), dir)) return null

    blockState = level.getBlockState(pos.offset(dir.opposite.normal).above())
    if (!blockState.isFaceSturdy(level, pos.offset(dir.opposite.normal).above(), dir)) return null

    return defaultBlockState().setValue(FACING, dir).setValue(HALF, false).setValue(SWING, 0)
  }

  public override fun neighborChanged(
    blockState: BlockState,
    level: Level,
    pos: BlockPos,
    block: Block,
    blockPos2: BlockPos,
    bl: Boolean
  ) {
    super.neighborChanged(blockState, level, pos, block, blockPos2, bl)

    val otherHalf = if (blockState.getValue(HALF)) Direction.DOWN else Direction.UP
    if (!level.getBlockState(pos.offset(otherHalf.normal)).`is`(this)) {
      level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
      return
    }

    val dir = blockState.getValue(FACING)
    val wallPos = pos.offset(dir.opposite.normal)
    val wallState = level.getBlockState(wallPos)
    if (!wallState.isFaceSturdy(level, wallPos, dir)) {
      level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())

      val half = blockState.getValue(HALF)
      if (half) {
        val itemStack = ItemStack(this)
        val center = pos.center
        val entity = ItemEntity(level, center.x, center.y, center.z, itemStack)
        level.addFreshEntity(entity)
      }
    }
  }

  override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
    super.createBlockStateDefinition(builder)
    builder.add(HALF, FACING, SWING)
  }

  fun swingTick(level: Level, pos: BlockPos, state: BlockState) {
    val targets: MutableList<BlockPos> = ArrayList()
    targets.add(pos)
    if (state.getValue(HALF)) targets.add(pos.below())
    else targets.add(pos.above())

    val facing = state.getValue(FACING)
    val xAxis = facing.axis === Direction.Axis.X
    var swing = 0

    for (i in level.getEntitiesOfClass(
      Entity::class.java,
      AABB.encapsulatingFullBlocks(pos, pos.above())
    )) {
      val v: Double = if (xAxis) i.deltaMovement.z
      else i.deltaMovement.x

      if (abs(v) > 0.1 && !i.isCrouching) swing = if (v > 0) 2 else 1

      if (i is LivingEntity) {
        val rot = (i.yBodyRot % 360 + 360) % 360
        swing = if (!xAxis) {
          if (rot > 180) 2
          else 1
        } else {
          if (rot < 90 || rot > 270) 2
          else 1
        }
      }
    }

    if (swing > 0) {
      if (level.getBlockState(pos.offset(facing.getNormal())).getBlock() is SaloonDoor) {
        val otherDoorPos = pos.offset(facing.getNormal())
        targets.add(otherDoorPos)
        if (level.getBlockState(otherDoorPos).getValue(HALF)) targets.add(otherDoorPos.below())
        else targets.add(otherDoorPos.above())
      }

      if (level.getBlockState(pos).getValue(SWING) == 0) level.playSound(
        null,
        pos,
        SoundEvents.WOODEN_TRAPDOOR_OPEN,
        SoundSource.BLOCKS
      )
      level.scheduleTick(pos, this, 10)
    } else {
      level.scheduleTick(pos, this, 1)
    }

    for (i in targets) {
      val iState: BlockState = level.getBlockState(i)
      if (iState.block is SaloonDoor) level.setBlockAndUpdate(i, iState.setValue(SWING, swing))
    }
  }
}