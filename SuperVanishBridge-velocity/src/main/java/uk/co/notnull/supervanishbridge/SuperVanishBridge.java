package uk.co.notnull.supervanishbridge;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;
import uk.co.notnull.supervanishbridge.api.SuperVanishBridgeAPI;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class SuperVanishBridge implements SuperVanishBridgeAPI {
	@Inject
	private Logger logger;
	@Inject
	private ProxyServer proxy;
	private final ChannelIdentifier stateChangeChannel = MinecraftChannelIdentifier.create("supervanish", "statechange");

	private final Set<UUID> vanished = ConcurrentHashMap.newKeySet();
	private final ConcurrentHashMap<UUID, Integer> vanishLevels = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<UUID, Integer> seeLevels = new ConcurrentHashMap<>();

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		boolean proxyChatEnabled = proxy.getPluginManager().isLoaded("proxychat");
		boolean proxyDiscordEnabled = proxy.getPluginManager().isLoaded("proxydiscord");

		proxy.getChannelRegistrar().register(stateChangeChannel);
	}

	@Subscribe(order = PostOrder.FIRST)
	public void onJoin(PlayerChooseInitialServerEvent event) {
		Player player = event.getPlayer();

		if(player.hasPermission("sv.joinvanished")) {
			handleStateChange(player, true,
							  getLayeredPermissionLevel(player, "use"),
							  getLayeredPermissionLevel(player, "see"));
		}
	}

	@Subscribe
	public void onPluginMessageEvent(PluginMessageEvent event) {
		if (event.getIdentifier().equals(stateChangeChannel)) {
			event.setResult(PluginMessageEvent.ForwardResult.handled());

			if (event.getSource() instanceof ServerConnection connection) {
				ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
				Player player = ((ServerConnection) event.getSource()).getPlayer();
				boolean vanished = in.readBoolean();
				int useLevel = in.readInt();
				int seeLevel = in.readInt();

				handleStateChange(player, vanished, useLevel, seeLevel);
			}
		}
	}

	private void handleStateChange(Player player, boolean state, int useLevel, int seeLevel) {
		if(state) {
			vanished.add(player.getUniqueId());
		} else {
			vanished.remove(player.getUniqueId());
		}

		vanishLevels.compute(player.getUniqueId(), (key, value) -> useLevel > 0 ? useLevel : null);
		seeLevels.compute(player.getUniqueId(), (key, value) -> seeLevel > 0 ? seeLevel : null);
	}

	public boolean isVanished(Player player) {
		return vanished.contains(player.getUniqueId());
	}

	public boolean isVanished(UUID uuid) {
		return vanished.contains(uuid);
	}

	public boolean canSee(Player player1, Player player2) {
		return canSee(player1.getUniqueId(), player2.getUniqueId());
	}

	public boolean canSee(UUID uuid1, UUID uuid2) {
		Integer player1Vanish = vanishLevels.computeIfAbsent(uuid1, key -> null);
		Integer player2See = seeLevels.computeIfAbsent(uuid1, key -> null);

		return player1Vanish == null || (player2See != null && player2See >= player1Vanish);
	}

	private int getLayeredPermissionLevel(CommandSource source, String permission) {
        int level = source.hasPermission("sv." + permission) ? 1 : 0;

        for (int i = 1; i <= 100; i++) {
			if (source.hasPermission("sv." + permission + ".level" + i)) {
				level = i;
			}
		}

        return level;
    }

	public Logger getLogger() {
		return logger;
	}

	public ProxyServer getProxy() {
		return proxy;
	}
}
