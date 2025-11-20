package net.ace;


import net.ace.network.NetworkRegistry;
import net.fabricmc.api.ModInitializer;

public class TweakLite implements ModInitializer {
	public static final String MOD_ID = "TweakLite";
	@Override
	public void onInitialize() {
		NetworkRegistry.initServer();
	}
}