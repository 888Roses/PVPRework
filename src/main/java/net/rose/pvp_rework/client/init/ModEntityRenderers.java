package net.rose.pvp_rework.client.init;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.rose.pvp_rework.client.render.entity.ChargoldHumblerSpikeEntityRenderer;
import net.rose.pvp_rework.client.render.entity.ChargoldScytheEntityRenderer;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.init.ModEntityTypes;

public class ModEntityRenderers {
    public static void init() {
        EntityRendererRegistry.register(ModEntityTypes.CHARGOLD_SCYTHE, ChargoldScytheEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.CHARGOLD_HUMBLER_SPIKE, ChargoldHumblerSpikeEntityRenderer::new);

        PVPRework.LOGGER.info("[{}] [Client] Initialized Mod Entity Renderers!", PVPRework.MOD_ID);
    }
}
