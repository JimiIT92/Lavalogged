package org.lavalogged.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lavalogged.Lavalogged;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin class for handling the use of a {@link BucketItem Bucket} on a {@link Lavaloggable Lavaloggable} {@link Block Block}
 */
@Mixin({
        AbstractBlock.class,
        AbstractSignBlock.class,
        CampfireBlock.class,
        CandleBlock.class,
        ChestBlock.class,
        FenceBlock.class,
        DecoratedPotBlock.class,
        EnderChestBlock.class,
        TrapdoorBlock.class
})
public class BucketUseMixin {

    /**
     * Make the {@link Block Block} {@link Lavalogged#LAVALOGGED Lavalogged} or not when a {@link BucketItem Bucket} is used on it
     *
     * @param blockState {@link BlockState The current Block State}
     * @param world {@link World The World reference}
     * @param blockPos {@link BlockPos The current Block Pos}
     * @param player {@link PlayerEntity The Player interacting with the Block}
     * @param hand {@link Hand The hand the Player is using}
     * @param blockHitResult {@link BlockHitResult The Block Hit Result}
     * @param callbackInfoReturnable {@link CallbackInfoReturnable<ActionResult> The Action Result Callback Info Returnable}
     */
    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void onUse(final BlockState blockState, final World world, final BlockPos blockPos, final PlayerEntity player, final Hand hand, final BlockHitResult blockHitResult, final CallbackInfoReturnable<ActionResult> callbackInfoReturnable) {
        if(this instanceof Lavaloggable && blockState.contains(Lavalogged.LAVALOGGED)) {
            ItemStack stack = player.getStackInHand(hand);
            if(!stack.isEmpty()) {
                if(stack.isOf(Items.LAVA_BUCKET) && !blockState.get(Lavalogged.LAVALOGGED)) {
                    world.setBlockState(blockPos, blockState.with(Lavalogged.LAVALOGGED, true));
                    world.scheduleFluidTick(blockPos, Fluids.LAVA, Fluids.LAVA.getTickRate(world));
                    player.playSound(SoundEvents.ITEM_BUCKET_EMPTY_LAVA, 1.0F, 1.0F);
                    player.swingHand(hand);
                    if(!player.isCreative()) {
                        stack.decrement(1);
                        player.getInventory().insertStack(Items.BUCKET.getDefaultStack());
                    }
                    callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
                    return;
                }
                if(stack.isOf(Items.BUCKET) && blockState.get(Lavalogged.LAVALOGGED)) {
                    world.setBlockState(blockPos, blockState.with(Lavalogged.LAVALOGGED, false));
                    player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
                    player.swingHand(hand);
                    if(!player.isCreative()) {
                        stack.decrement(1);
                        player.getInventory().insertStack(Items.LAVA_BUCKET.getDefaultStack());
                    }
                    callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }

}