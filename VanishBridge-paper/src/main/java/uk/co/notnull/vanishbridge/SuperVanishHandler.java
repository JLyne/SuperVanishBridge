package uk.co.notnull.vanishbridge;

import de.myzelyam.api.vanish.PlayerVanishStateChangeEvent;
import de.myzelyam.supervanish.SuperVanish;
import de.myzelyam.supervanish.VanishPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SuperVanishHandler implements Listener {
	private final VanishBridge plugin;
	private final SuperVanish superVanish = (SuperVanish) Bukkit.getServer().getPluginManager().getPlugin("SuperVanish");

	public SuperVanishHandler(VanishBridge plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerVanishStateChange(PlayerVanishStateChangeEvent event) {
		Player player = plugin.getServer().getPlayer(event.getUUID());

		if(player == null || !player.isOnline()) {
			return;
		}

		VanishPlayer vanishPlayer = superVanish.getVanishPlayer(plugin.getServer().getPlayer(event.getUUID()));

		plugin.sendPluginMessage(player, event.isVanishing(), vanishPlayer.getUsePermissionLevel(),
								 vanishPlayer.getSeePermissionLevel());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
			Player player = event.getPlayer();

			if(!player.isOnline()) {
				return;
			}

			VanishPlayer vanishPlayer = superVanish.getVanishPlayer(player);

			plugin.sendPluginMessage(player, vanishPlayer.isOnlineVanished(), vanishPlayer.getUsePermissionLevel(),
								 vanishPlayer.getSeePermissionLevel());
		}, 1L);
	}
}
