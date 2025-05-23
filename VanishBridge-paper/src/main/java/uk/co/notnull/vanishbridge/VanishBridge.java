package uk.co.notnull.vanishbridge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.ByteBuffer;


public class VanishBridge extends JavaPlugin implements Listener {
	public void onEnable() {
		getServer().getMessenger().registerOutgoingPluginChannel(this, "vanish:statechange");
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onPluginEnable(PluginEnableEvent event) {
		if(event.getPlugin().getName().equals("SuperVanish")) {
			getLogger().info("Enabling SuperVanish integration");
			new SuperVanishHandler(this);
		}
	}

	void sendPluginMessage(Player player, boolean state) {
		sendPluginMessage(player, state, 1, 1);
	}

	void sendPluginMessage(Player player, boolean state, int useLevel, int seeLevel) {
		byte[] data = ByteBuffer.allocate(9)
				.put((byte) (state ? 1 : 0))
				.putInt(useLevel)
				.putInt(seeLevel)
				.array();

		if(player != null) {
			getLogger().info("Propagating vanish state of " + player.getName() + ": " + state);
			player.sendPluginMessage(this, "vanish:statechange", data);
		}
	}
}
