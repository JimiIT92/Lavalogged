package org.lavalogged.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import org.lavalogged.Lavalogged;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Shadow private BlockState defaultState;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(AbstractBlock .Settings settings, CallbackInfo callbackInfo) {
        if(this instanceof Lavaloggable && defaultState.contains(Lavalogged.LAVALOGGED)) {
            defaultState = defaultState.with(Lavalogged.LAVALOGGED, false);
        }
    }

    @Inject(method = "setDefaultState", at = @At("TAIL"))
    public void setDefaultState(BlockState blockState, CallbackInfo callbackInfo) {
        if(this instanceof Lavaloggable && defaultState.contains(Lavalogged.LAVALOGGED)) {
            defaultState = defaultState.with(Lavalogged.LAVALOGGED, false);
        }
    }

    @Inject(method = "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;", at = @At("RETURN"), cancellable = true)
    private void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (this instanceof Lavaloggable && cir.getReturnValue() != null && cir.getReturnValue().contains(Lavalogged.LAVALOGGED)) {
            cir.setReturnValue(cir.getReturnValue().with(Lavalogged.LAVALOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.LAVA));
        }
    }

}