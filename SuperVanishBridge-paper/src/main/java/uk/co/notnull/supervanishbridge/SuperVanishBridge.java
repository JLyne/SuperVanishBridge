package uk.co.notnull.supervanishbridge;

import de.myzelyam.api.vanish.PlayerVanishStateChangeEvent;
import de.myzelyam.supervanish.SuperVanish;
import de.myzelyam.supervanish.VanishPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.ByteBuffer;


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

		sendPluginMessage(superVanish.getVanishPlayer(getServer().getPlayer(event.getUUID())));
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {

		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
			Player player = event.getPlayer();

			if(!player.isOnline()) {
				return;
			}

			sendPluginMessage(superVanish.getVanishPlayer(player));
		}, 1L);
	}

	private void sendPluginMessage(VanishPlayer vanishPlayer) {
		byte[] data = ByteBuffer.allocate(9)
				.put((byte) (vanishPlayer.isOnlineVanished() ? 1 : 0))
				.putInt(vanishPlayer.getUsePermissionLevel())
				.putInt(vanishPlayer.getSeePermissionLevel())
				.array();
		vanishPlayer.getPlayer().sendPluginMessage(this, "supervanish:statechange", data);
	}
}
