package net.rose.pvp_rework.api.util;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class SoundUtil {
    public static void playSound(
            @NotNull World world, Vec3d pos,
            @NotNull SoundEvent event, @NotNull SoundCategory category,
            float volume, float pitch
    ) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.playSound(
                    null, pos.x, pos.y, pos.z,
                    event, category,
                    volume, pitch
            );
        }
    }

    public static void playSound(
            @NotNull World world, Vec3d pos,
            @NotNull SoundEvent event, SoundCategory category
    ) {
        playSound(world, pos, event, category, 1F, 1F);
    }
}
