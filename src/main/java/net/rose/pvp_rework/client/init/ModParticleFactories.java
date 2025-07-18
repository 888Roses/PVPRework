package net.rose.pvp_rework.client.init;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.rose.pvp_rework.client.particle.ChargoldHumblerWarningParticle;
import net.rose.pvp_rework.client.particle.ChargoldSparkParticle;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.init.ModParticles;

public class ModParticleFactories {
    public static void init() {
        var registry = ParticleFactoryRegistry.getInstance();
        registry.register(ModParticles.CHARGOLD_SPARK, ChargoldSparkParticle.Factory::new);
        registry.register(ModParticles.CHARGOLD_HUMBLER_WARNING, ChargoldHumblerWarningParticle.Factory::new);

        PVPRework.LOGGER.info("[{}] [Client]: Initialized Mod Particle Factories!", PVPRework.MOD_ID);
    }
}
