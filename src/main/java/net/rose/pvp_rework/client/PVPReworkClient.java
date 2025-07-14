package net.rose.pvp_rework.client;

import net.fabricmc.api.ClientModInitializer;
import net.rose.pvp_rework.common.init.ModNetworking;

public class PVPReworkClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModNetworking.createClientGlobalReceivers();
    }
}
