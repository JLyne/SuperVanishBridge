package uk.co.notnull.vanishbridge.api;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface VanishBridgeAPI {
	boolean isVanished(Player player);

	boolean isVanished(UUID uuid);

	boolean canSee(Player player1, Player player2);

	boolean canSee(UUID uuid1, UUID uuid2);

	List<Player> getPlayerSuggestions(String query, @Nullable CommandSource source);

	List<String> getUsernameSuggestions(String query, @Nullable CommandSource source);
}
