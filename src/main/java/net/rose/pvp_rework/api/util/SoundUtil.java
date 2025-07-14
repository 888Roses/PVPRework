package net.rose.pvp_rework.api.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rose.pvp_rework.common.init.ModSounds;
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

    public static void playSoundWithDistance(
            @NotNull World world, @NotNull Vec3d pos,
            @NotNull SoundEvent event, @NotNull SoundCategory category,
            float volume, float pitch, float maxDistance
    ) {
        if (!world.isClient) {
            return;
        }

        final var player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }

        final var distance = (float) pos.distanceTo(player.getEyePos());
        if (distance <= maxDistance) {
            var effectiveVolume = 1f - (distance / maxDistance);
            effectiveVolume = MathHelper.clamp(effectiveVolume, 0, 1) * volume;
            MinecraftClient.getInstance().getSoundManager().play(new PositionedSoundInstance(
                    event, category, effectiveVolume, pitch, player.getRandom(), player.getBlockPos()
            ));
        }
    }
}
