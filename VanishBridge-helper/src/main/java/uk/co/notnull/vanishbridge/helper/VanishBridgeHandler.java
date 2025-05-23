package uk.co.notnull.vanishbridge.helper;

import com.velocitypowered.api.plugin.PluginContainer;
import uk.co.notnull.vanishbridge.api.VanishBridgeAPI;

public class VanishBridgeHandler {
	private final VanishBridgeAPI vanishBridgeAPI;

	public VanishBridgeHandler(PluginContainer vanishBridge) {
		vanishBridgeAPI = (VanishBridgeAPI) vanishBridge.getInstance().get();
	}

	public VanishBridgeAPI getApi() {
		return vanishBridgeAPI;
	}
}
