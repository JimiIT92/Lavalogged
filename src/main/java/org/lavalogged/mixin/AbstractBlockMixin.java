package org.lavalogged.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import org.lavalogged.Lavalogged;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin class for the {@link AbstractBlock Abstract Block class}
 */
@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    /**
     * Get the {@link FluidState Fluid State} of a {@link Lavalogged#LAVALOGGED Lavalogged} {@link Block Block}
     *
     * @param blockState {@link BlockState The current Block State}
     * @param callbackInfoReturnable {@link CallbackInfoReturnable<FluidState> The Fluid State Callback Info Returnable}
     */
    @Inject(method = "getFluidState", at = @At("RETURN"), cancellable = true)
    private void getFluidState(final BlockState blockState, final CallbackInfoReturnable<FluidState> callbackInfoReturnable) {
        if (this instanceof Lavaloggable && blockState.contains(Lavalogged.LAVALOGGED) && blockState.get(Lavalogged.LAVALOGGED)) {
            callbackInfoReturnable.setReturnValue(Fluids.LAVA.getStill(false));
        }
    }

}