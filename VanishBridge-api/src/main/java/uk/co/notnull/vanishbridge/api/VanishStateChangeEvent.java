package uk.co.notnull.vanishbridge.api;

import com.velocitypowered.api.proxy.Player;

public class VanishStateChangeEvent {
	private Player player;
	private boolean isVanishing;

	public VanishStateChangeEvent(Player player, boolean isVanishing) {
		this.player = player;
		this.isVanishing = isVanishing;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isVanishing() {
		return isVanishing;
	}
}
