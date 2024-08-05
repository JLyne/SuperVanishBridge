package uk.co.notnull.supervanishbridge;

import de.myzelyam.api.vanish.PlayerVanishStateChangeEvent;
import de.myzelyam.supervanish.SuperVanish;
import de.myzelyam.supervanish.VanishPlayer;
import org.bukkit.Bukkit;
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

		sendPluginMessage(superVanish.getVanishPlayer(getServer().getPlayer(event.getUUID())), event.isVanishing());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
			Player player = event.getPlayer();

			if(!player.isOnline()) {
				return;
			}

			VanishPlayer vanishPlayer = superVanish.getVanishPlayer(player);

			sendPluginMessage(vanishPlayer, vanishPlayer.isOnlineVanished());
		}, 1L);
	}

	private void sendPluginMessage(VanishPlayer vanishPlayer, boolean state) {
		byte[] data = ByteBuffer.allocate(9)
				.put((byte) (state ? 1 : 0))
				.putInt(vanishPlayer.getUsePermissionLevel())
				.putInt(vanishPlayer.getSeePermissionLevel())
				.array();

		Player player = Bukkit.getPlayer(vanishPlayer.getPlayerUUID());

		if(player != null) {
			getLogger().info("Propagating vanish state of " + player.getName() + ": " + state);
			player.sendPluginMessage(this, "supervanish:statechange", data);
		}
	}
}
