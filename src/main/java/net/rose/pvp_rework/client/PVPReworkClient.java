package net.rose.pvp_rework.client;

import net.fabricmc.api.ClientModInitializer;
import net.rose.pvp_rework.client.init.ModEntityRenderers;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.init.ModNetworking;

public class PVPReworkClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PVPRework.LOGGER.info("[{}] [Client] Initialized Client Initializer!", PVPRework.MOD_ID);

        ModNetworking.createClientGlobalReceivers();
        ModItemModels.init();
        ModEntityRenderers.init();
    }
}
