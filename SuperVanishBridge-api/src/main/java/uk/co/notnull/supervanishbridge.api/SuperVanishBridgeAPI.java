package uk.co.notnull.supervanishbridge.api;

import com.velocitypowered.api.proxy.Player;

import java.util.UUID;

@SuppressWarnings("unused")
public interface SuperVanishBridgeAPI {
	boolean isVanished(Player player);

	boolean isVanished(UUID uuid);

	boolean canSee(Player player1, Player player2);

	boolean canSee(UUID uuid1, UUID uuid2);
}
