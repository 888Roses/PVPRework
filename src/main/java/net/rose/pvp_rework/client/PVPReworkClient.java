package net.rose.pvp_rework.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.rose.pvp_rework.client.render.entity.ChargoldScytheEntityRenderer;
import net.rose.pvp_rework.common.init.ModEntityTypes;
import net.rose.pvp_rework.common.init.ModNetworking;

public class PVPReworkClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModNetworking.createClientGlobalReceivers();
        ModItemModels.init();

        EntityRendererRegistry.register(ModEntityTypes.CHARGOLD_SCYTHE, ChargoldScytheEntityRenderer::new);
    }
}
