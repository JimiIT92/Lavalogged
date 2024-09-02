package org.lavalogged.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.lavalogged.Lavalogged;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"))
    private void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (this instanceof Lavaloggable && state.contains(Lavalogged.LAVALOGGED) && state.get(Lavalogged.LAVALOGGED)) {
            world.scheduleFluidTick(pos, Fluids.LAVA, Fluids.LAVA.getTickRate(world));
        }
    }

    @Inject(method = "getFluidState", at = @At("RETURN"), cancellable = true)
    private void getFluidState(BlockState state, CallbackInfoReturnable<FluidState> cir) {
        if (this instanceof Lavaloggable && state.contains(Lavalogged.LAVALOGGED) && state.get(Lavalogged.LAVALOGGED)) {
            cir.setReturnValue(Fluids.LAVA.getStill(false));
        }
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if(this instanceof Lavaloggable && state.contains(Lavalogged.LAVALOGGED)) {
            ItemStack stack = player.getStackInHand(hand);
            if(!stack.isEmpty()) {
                if(stack.isOf(Items.LAVA_BUCKET) && !state.get(Lavalogged.LAVALOGGED)) {
                    world.setBlockState(pos, state.with(Lavalogged.LAVALOGGED, true));
                    player.playSound(SoundEvents.ITEM_BUCKET_EMPTY_LAVA, 1.0F, 1.0F);
                    player.swingHand(hand);
                    if(!player.isCreative()) {
                        stack.decrement(1);
                        player.getInventory().insertStack(Items.BUCKET.getDefaultStack());
                    }
                    cir.setReturnValue(ActionResult.SUCCESS);
                    return;
                }
                if(stack.isOf(Items.BUCKET) && state.get(Lavalogged.LAVALOGGED)) {
                    world.setBlockState(pos, state.with(Lavalogged.LAVALOGGED, false));
                    player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
                    player.swingHand(hand);
                    if(!player.isCreative()) {
                        stack.decrement(1);
                        player.getInventory().insertStack(Items.LAVA_BUCKET.getDefaultStack());
                    }
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}