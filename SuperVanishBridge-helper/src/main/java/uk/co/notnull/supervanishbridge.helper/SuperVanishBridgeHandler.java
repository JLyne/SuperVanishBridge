package uk.co.notnull.supervanishbridge.helper;

import com.velocitypowered.api.plugin.PluginContainer;
import uk.co.notnull.supervanishbridge.api.SuperVanishBridgeAPI;

public class SuperVanishBridgeHandler {
	private final SuperVanishBridgeAPI superVanishBridgeAPI;

	public SuperVanishBridgeHandler(PluginContainer superVanishBridge) {
		superVanishBridgeAPI = (SuperVanishBridgeAPI) superVanishBridge.getInstance().get();
	}

	public SuperVanishBridgeAPI getApi() {
		return superVanishBridgeAPI;
	}
}
