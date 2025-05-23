package uk.co.notnull.vanishbridge;

import simplexity.simpleVanish.events.PlayerVanishEvent;
import simplexity.simpleVanish.events.PlayerUnvanishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class SimpleVanishHandler implements Listener {
	private final VanishBridge plugin;

	public SimpleVanishHandler(VanishBridge plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerVanish(PlayerVanishEvent event) {
		plugin.sendPluginMessage(event.getPlayer(), true);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerUnvanish(PlayerUnvanishEvent event) {
		plugin.sendPluginMessage(event.getPlayer(), false);
	}
}
