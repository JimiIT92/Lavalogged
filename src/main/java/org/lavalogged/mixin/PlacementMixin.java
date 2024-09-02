package org.lavalogged.mixin;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import org.lavalogged.Lavalogged;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
        AbstractRailBlock.class,
        SignBlock.class,
        WallSignBlock.class,
        HangingSignBlock.class,
        WallHangingSignBlock.class,
        AmethystClusterBlock.class,
        BarrierBlock.class,
        BigDripleafBlock.class,
        CampfireBlock.class,
        CandleBlock.class,
        ChainBlock.class,
        ChestBlock.class,
        ConduitBlock.class,
        CoralParentBlock.class,
        DecoratedPotBlock.class,
        EnderChestBlock.class,
        GrateBlock.class,
        HangingRootsBlock.class,
        Block.class,
        LadderBlock.class,
        LanternBlock.class,
        LeavesBlock.class,
        LightningRodBlock.class,
        MangroveRootsBlock.class,
        PointedDripstoneBlock.class,
        PropaguleBlock.class,
        ScaffoldingBlock.class,
        SculkSensorBlock.class,
        MultifaceGrowthBlock.class,
        SeaPickleBlock.class,
        SlabBlock.class,
        SmallDripleafBlock.class,
        StairsBlock.class,
        TrapdoorBlock.class,
        WallBlock.class
})
public class PlacementMixin {

    @Inject(method = "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;", at = @At("RETURN"), cancellable = true)
    private void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (this instanceof Lavaloggable && cir.getReturnValue() != null && cir.getReturnValue().contains(Lavalogged.LAVALOGGED)) {
            cir.setReturnValue(cir.getReturnValue().with(Lavalogged.LAVALOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.LAVA));
        }
    }
}
