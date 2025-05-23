package uk.co.notnull.supervanishbridge.api;

import com.velocitypowered.api.proxy.Player;

@Deprecated(forRemoval = true)
public class VanishStateChangeEvent extends uk.co.notnull.vanishbridge.api.VanishStateChangeEvent {
	public VanishStateChangeEvent(Player player, boolean isVanishing) {
		super(player, isVanishing);
	}
}
