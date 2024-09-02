package org.lavalogged;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.BooleanProperty;
import org.lavalogged.mixin.Lavaloggable;
import virtuoel.statement.api.StateRefresher;

public final class Lavalogged implements ModInitializer {

	public static final String MOD_ID = "lavalogged";

	public static final BooleanProperty LAVALOGGED = BooleanProperty.of("lavalogged");

	@Override
	public void onInitialize() {
		Registries.BLOCK.getEntrySet().stream().filter(block -> block.getValue() instanceof Lavaloggable).forEach(block -> StateRefresher.INSTANCE.addBlockProperty(block.getValue(), LAVALOGGED, false));
		StateRefresher.INSTANCE.reorderBlockStates();
	}
}