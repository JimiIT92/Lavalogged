package org.lavalogged.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.lavalogged.Lavalogged;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin class for the {@link Block Block class}
 */
@Mixin(Block.class)
public abstract class BlockMixin {

    /**
     * The {@link BlockState Default Block State}
     */
    @Shadow private BlockState defaultState;

    /**
     * Set the {@link Block Block} {@link #defaultState Default Block State}
     *
     * @param settings {@link AbstractBlock.Settings The Block Settings}
     * @param callbackInfo {@link CallbackInfo The Block Callback Info}
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(final AbstractBlock.Settings settings, final CallbackInfo callbackInfo) {
        setDefaultState();
    }

    /**
     * Set the {@link Block Block} {@link #defaultState Default Block State}
     *
     * @param blockState {@link BlockState The current Block State}
     * @param callbackInfo {@link CallbackInfo The Block Callback Info}
     */
    @Inject(method = "setDefaultState", at = @At("TAIL"))
    public void setDefaultState(final BlockState blockState, final CallbackInfo callbackInfo) {
        setDefaultState();
    }

    /**
     * Set the {@link Block Block} {@link #defaultState Default Block State}
     */
    @Unique
    private void setDefaultState() {
        if(this instanceof Lavaloggable && defaultState.contains(Lavalogged.LAVALOGGED)) {
            defaultState = defaultState.with(Lavalogged.LAVALOGGED, false);
        }
    }

}