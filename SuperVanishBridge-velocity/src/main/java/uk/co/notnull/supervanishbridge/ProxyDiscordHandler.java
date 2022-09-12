package uk.co.notnull.supervanishbridge;

import com.velocitypowered.api.event.Subscribe;
import uk.co.notnull.proxydiscord.api.events.PlayerInfoEvent;

public class ProxyDiscordHandler {
	SuperVanishBridge plugin;
	public ProxyDiscordHandler(SuperVanishBridge plugin) {
		this.plugin = plugin;
		plugin.getProxy().getEventManager().register(plugin, this);
	}

	@Subscribe
	public void onPlayerInfo(PlayerInfoEvent event) {
		if(plugin.isVanished(event.getPlayerInfo().getUuid())) {
			event.getPlayerInfo().setVanished(true);
		}
	}
}
