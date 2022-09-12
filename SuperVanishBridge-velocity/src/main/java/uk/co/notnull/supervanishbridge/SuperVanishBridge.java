package uk.co.notnull.supervanishbridge;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
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

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class SuperVanishBridge {
	@Inject
	private Logger logger;
	@Inject
	private ProxyServer proxy;
	private final ChannelIdentifier stateChangeChannel = MinecraftChannelIdentifier.create("supervanish", "statechange");

	private final Set<UUID> vanished = ConcurrentHashMap.newKeySet();
	private ProxyChatHandler proxyChatHandler;
	private ProxyDiscordHandler proxyDiscordHandler;

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		boolean proxyChatEnabled = proxy.getPluginManager().isLoaded("proxychat");
		boolean proxyDiscordEnabled = proxy.getPluginManager().isLoaded("proxydiscord");

		if(proxyChatEnabled) {
			proxyChatHandler = new ProxyChatHandler(this);
		}

		if(proxyDiscordEnabled) {
			proxyDiscordHandler = new ProxyDiscordHandler(this);
		}

		proxy.getChannelRegistrar().register(stateChangeChannel);
	}

	@Subscribe(order = PostOrder.FIRST)
	public void onJoin(PlayerChooseInitialServerEvent event) {
		Player player = event.getPlayer();

		if(player.hasPermission("sv.joinvanished")) {
			vanished.add(player.getUniqueId());
		}
	}

	@Subscribe
	public void onPluginMessageEvent(PluginMessageEvent event) {
		if (event.getIdentifier().equals(stateChangeChannel)) {
			event.setResult(PluginMessageEvent.ForwardResult.handled());

			if (event.getSource() instanceof ServerConnection connection) {
				ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
				Player player = ((ServerConnection) event.getSource()).getPlayer();
				boolean state = in.readBoolean();

				handleStateChange(player, state);
			}
		}
	}

	private void handleStateChange(Player player, boolean state) {
		if(state) {
			vanished.add(player.getUniqueId());
		} else {
			vanished.remove(player.getUniqueId());
		}

		if(isProxyChatEnabled()) {
			proxyChatHandler.handleStateChange(player, state);
		}
	}

	public boolean isVanished(Player player) {
		return vanished.contains(player.getUniqueId());
	}

	public boolean isVanished(UUID uuid) {
		return vanished.contains(uuid);
	}

	private boolean isProxyChatEnabled() {
		return proxyChatHandler != null;
	}

	private boolean isProxyDiscordEnabled() {
		return proxyDiscordHandler != null;
	}

	public Logger getLogger() {
		return logger;
	}

	public ProxyServer getProxy() {
		return proxy;
	}
}
