package io.github.robustum.teatopia.common.block

import io.github.robustum.teatopia.common.TeatopiaItems
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*
import kotlin.math.min

@Suppress("OVERRIDE_DEPRECATION")
class TeaTreeBlock : PlantBlock(FabricBlockSettings.of(Material.PLANT)), Fertilizable {
    companion object {
        const val MAX_AGE = 5

        @JvmStatic
        val AGE: IntProperty = Properties.AGE_5
    }

    init {
        defaultState = defaultState.with(AGE, MAX_AGE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun getPickStack(world: BlockView?, pos: BlockPos?, state: BlockState?): ItemStack {
        return ItemStack(TeatopiaItems.freshTeaLeaf)
    }

    override fun isFertilizable(world: BlockView, pos: BlockPos, state: BlockState, isClient: Boolean): Boolean {
        return state.get(AGE) < MAX_AGE
    }

    override fun canGrow(world: World, random: Random, pos: BlockPos, state: BlockState): Boolean {
        return true
    }

    override fun grow(world: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {
        val i = min(MAX_AGE, state.get(AGE) + 1)
        world.setBlockState(pos, state.with(AGE, i), Block.NOTIFY_LISTENERS)
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult,
    ): ActionResult {
        val age = state.get(AGE)
        val isMature = age == 5
        if (!isMature && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS
        }
        if (isMature) {
            val dropStack = if (player.getStackInHand(hand).isOf(Items.SHEARS)) {
                ItemStack(TeatopiaItems.teaSapling, world.random.nextInt(1, 3))
            } else {
                ItemStack(TeatopiaItems.freshTeaLeaf, world.random.nextInt(1, 4))
            }
            Block.dropStack(world, pos, dropStack)
            world.playSound(
                null,
                pos,
                SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES,
                SoundCategory.BLOCKS,
                1.0f,
                0.8f + world.random.nextFloat() + 0.4f
            )
            world.setBlockState(pos, state.with(AGE, 1), Block.NOTIFY_LISTENERS)
            return ActionResult.success(world.isClient)
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }
}