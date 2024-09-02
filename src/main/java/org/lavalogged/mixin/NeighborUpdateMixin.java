package org.lavalogged.mixin;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
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
        LightBlock.class,
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
public class NeighborUpdateMixin {

    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"))
    private void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (this instanceof Lavaloggable && state.contains(Lavalogged.LAVALOGGED) && state.get(Lavalogged.LAVALOGGED)) {
            world.scheduleFluidTick(pos, Fluids.LAVA, Fluids.LAVA.getTickRate(world));
        }
    }
}
