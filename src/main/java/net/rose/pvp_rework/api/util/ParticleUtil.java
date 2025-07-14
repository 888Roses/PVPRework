package net.rose.pvp_rework.api.util;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleUtil {
    public static <T extends ParticleEffect> void spawnParticles(
            World world, T particle,
            Vec3d pos, Vec3d spread,
            int count, double speed
    ) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    particle, pos.x, pos.y, pos.z,
                    count, spread.x, spread.y, spread.z, speed
            );
        }
    }
}
