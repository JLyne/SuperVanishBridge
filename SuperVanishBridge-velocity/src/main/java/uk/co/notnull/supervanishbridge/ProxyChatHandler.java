package uk.co.notnull.supervanishbridge;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.Player;
import uk.co.notnull.ProxyChat.api.account.AccountManager;
import uk.co.notnull.ProxyChat.api.event.ProxyChatJoinEvent;

public class ProxyChatHandler {
	private final SuperVanishBridge plugin;

	public ProxyChatHandler(SuperVanishBridge plugin) {
		this.plugin = plugin;
		plugin.getProxy().getEventManager().register(plugin, this);
	}

	@Subscribe(order = PostOrder.EARLY)
	public void onPlayerInfo(ProxyChatJoinEvent event) {
		Player player = event.getPlayer();

		AccountManager.getAccount(player.getUniqueId())
				.ifPresent(a -> {
					a.setVanished(plugin.isVanished(player.getUniqueId()));
				});
	}

	public void handleStateChange(Player player, boolean state) {
		AccountManager.getAccount(player.getUniqueId()).ifPresent(a -> a.setVanished(state));
	}
}
