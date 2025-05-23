package uk.co.notnull.supervanishbridge.helper;

import com.google.inject.Inject;
import com.velocitypowered.api.proxy.ProxyServer;

@Deprecated(forRemoval = true)
@SuppressWarnings("unused")
public class SuperVanishBridgeHelper extends uk.co.notnull.vanishbridge.helper.VanishBridgeHelper {
	@Inject
	public SuperVanishBridgeHelper(ProxyServer proxyServer) {
		super(proxyServer);
	}
}
