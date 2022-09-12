package uk.co.notnull.supervanishbridge;

import de.myzelyam.api.vanish.PlayerVanishStateChangeEvent;
import de.myzelyam.supervanish.SuperVanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class SuperVanishBridge extends JavaPlugin implements Listener {
	private SuperVanish superVanish;

	public void onEnable() {
		superVanish = (SuperVanish) getServer().getPluginManager().getPlugin("SuperVanish");
		getServer().getMessenger().registerOutgoingPluginChannel(this, "supervanish:statechange");
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerVanishStateChange(PlayerVanishStateChangeEvent event) {
		Player player = getServer().getPlayer(event.getUUID());

		if(player == null || !player.isOnline()) {
			return;
		}

		sendPluginMessage(player, event.isVanishing());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {

		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
			Player player = event.getPlayer();

			if(!player.isOnline()) {
				return;
			}

			sendPluginMessage(player,  superVanish.getVanishPlayer(player).isOnlineVanished());
		}, 1L);
	}

	private void sendPluginMessage(Player player, boolean state) {
		player.sendPluginMessage(this, "supervanish:statechange", new byte[]{ (byte) (state ? 1 : 0) });
	}
}
