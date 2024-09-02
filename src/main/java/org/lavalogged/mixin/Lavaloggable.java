package org.lavalogged.mixin;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({
       AbstractRailBlock.class, AbstractSignBlock.class, AmethystClusterBlock.class, BarrierBlock.class, BigDripleafBlock.class,
       CampfireBlock.class, CandleBlock.class, ChainBlock.class, ChestBlock.class, ConduitBlock.class, CoralParentBlock.class,
        DecoratedPotBlock.class, EnderChestBlock.class, GlowLichenBlock.class, GrateBlock.class, HangingRootsBlock.class,
        HorizontalConnectingBlock.class, LadderBlock.class, LanternBlock.class, LeavesBlock.class, LightBlock.class, LightningRodBlock.class,
        MangroveRootsBlock.class, PointedDripstoneBlock.class, PropaguleBlock.class, ScaffoldingBlock.class, SculkSensorBlock.class,
        SculkVeinBlock.class, SeaPickleBlock.class, SlabBlock.class, SmallDripleafBlock.class, StairsBlock.class, TrapdoorBlock.class, WallBlock.class
})
public interface Lavaloggable extends Waterloggable { }
