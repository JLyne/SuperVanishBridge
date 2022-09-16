package uk.co.notnull.supervanishbridge.helper;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class SuperVanishBridgeHelper {
	private final ProxyServer proxyServer;
	private SuperVanishBridgeHandler handler = null;

	private static SuperVanishBridgeHelper instance;

	@Inject
	public SuperVanishBridgeHelper(ProxyServer proxyServer) {
		SuperVanishBridgeHelper.instance = this;
		this.proxyServer = proxyServer;

		if(proxyServer.getPluginManager().isLoaded("supervanishbridge")) {
			this.handler = new SuperVanishBridgeHandler(proxyServer.getPluginManager()
																.getPlugin("supervanishbridge").get());
		}
	}

	public boolean isVanished(Player player) {
		return handler != null && handler.getApi().isVanished(player);
	}

	public boolean isVanished(UUID uuid) {
		return handler != null && handler.getApi().isVanished(uuid);
	}

	public boolean canSee(Player player1, Player player2) {
		return handler == null || handler.getApi().canSee(player1, player2);
	}

	public boolean canSee(UUID uuid1, UUID uuid2) {
		return handler == null || handler.getApi().canSee(uuid1, uuid2);
	}

	public List<Player> getPlayerSuggestions(String query, @Nullable CommandSource source) {
		if(handler != null) {
			return handler.getApi().getPlayerSuggestions(query, source);
		} else {
			return new ArrayList<>(proxyServer.matchPlayer(query));
		}
	}

	public List<String> getUsernameSuggestions(String query, @Nullable CommandSource source) {
		if(handler != null) {
			return handler.getApi().getUsernameSuggestions(query, source);
		} else {
			return proxyServer.matchPlayer(query).stream().map(Player::getUsername).collect(Collectors.toList());
		}
	}

	public void ifLoaded(Function<SuperVanishBridgeHandler, Void> callback) {
		if(handler != null) {
			callback.apply(handler);
		}
	}

	public static SuperVanishBridgeHelper getInstance() {
		return instance;
	}
}
