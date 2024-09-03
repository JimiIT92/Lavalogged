package org.lavalogged;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.BooleanProperty;
import org.lavalogged.mixin.Lavaloggable;
import virtuoel.statement.api.StateRefresher;

/**
 * {@link Lavalogged Lavalogged}
 * Make waterloggable blocks also be able to be lavalogged!
 */
public final class Lavalogged implements ModInitializer {

	/**
	 * The {@link BooleanProperty Lavalogged} property
	 */
	public static final BooleanProperty LAVALOGGED = BooleanProperty.of("lavalogged");

	/**
	 * Initialize the mod
	 */
	@Override
	public void onInitialize() {
		Registries.BLOCK.getEntrySet().stream().filter(block -> block.getValue() instanceof Lavaloggable).forEach(block -> StateRefresher.INSTANCE.addBlockProperty(block.getValue(), LAVALOGGED, false));
		StateRefresher.INSTANCE.reorderBlockStates();
	}

}