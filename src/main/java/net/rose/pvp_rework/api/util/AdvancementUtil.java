package net.rose.pvp_rework.api.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.rose.pvp_rework.api.API;

public class AdvancementUtil {
    public static boolean grantAdvancement(PlayerEntity player, String name) {
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            final var server = serverPlayerEntity.server;
            final var advancementLoader = server.getAdvancementLoader();
            final var advancement = advancementLoader.get(API.id(name));
            if (advancement != null) {
                final var tracker = serverPlayerEntity.getAdvancementTracker();
                return tracker.grantCriterion(advancement, name);
            }
        }

        return false;
    }
}
