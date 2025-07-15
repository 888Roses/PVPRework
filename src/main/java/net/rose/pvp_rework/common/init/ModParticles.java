package net.rose.pvp_rework.common.init;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.rose.pvp_rework.common.PVPRework;

import java.util.function.Function;

public class ModParticles {
    public static final DefaultParticleType CHARGOLD_SPARK = of("chargold_spark");

    private static DefaultParticleType of(String name) {
        var particle = FabricParticleTypes.simple();
        Registry.register(Registries.PARTICLE_TYPE, PVPRework.id(name), particle);
        return particle;
    }

    private static <T extends ParticleEffect> ParticleType<T> of(String name, boolean alwaysShow,
                                                                 ParticleEffect.Factory<T> factory,
                                                                 final Function<ParticleType<T>, Codec<T>> codecGetter
    ) {
        return Registry.register(
                Registries.PARTICLE_TYPE,
                PVPRework.id(name),
                new ParticleType<T>(alwaysShow, factory) {
                    public Codec<T> getCodec() {
                        return codecGetter.apply(this);
                    }
                });
    }

    public static void init() {
        PVPRework.LOGGER.info("[{}]: Initialized Mod Particles!", PVPRework.MOD_ID);
    }
}
