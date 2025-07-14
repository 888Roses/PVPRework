package net.rose.pvp_rework.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.rose.pvp_rework.api.item.model.ItemContextualModelInfo;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.init.ModEntityComponents;
import net.rose.pvp_rework.common.init.ModItems;

public class ModItemModels {
    public static final ItemContextualModelInfo CHARGOLD_SCYTHE =
            ItemContextualModelInfo.create(ModItems.CHARGOLD_SCYTHE)
                    .with("chargold_scythe_outline", info -> {
                        var livingEntity = info.livingEntity();
                        // If the living entity is null, it means it is our client player, so we can set it to that.
                        if (livingEntity == null) livingEntity = MinecraftClient.getInstance().player;

                        // Should never be happening, only for safety measures.
                        if (livingEntity == null) {
                            PVPRework.LOGGER.error("[ModItemModels]: Could not find owner living entity for model 'chargold_scythe_outline'!");
                            return false;
                        }

                        final var component = ModEntityComponents.CHARGOLD_SCYTHE.get(livingEntity);
                        return component.isThrown() && info.mode() != ModelTransformationMode.FIXED;
                    })
                    .with("chargold_scythe_handheld", ModItemModels::isHandheld)
                    .register();

    // region Util

    public static boolean isOwner(ItemContextualModelInfo.ContextInfo info) {
        if (!info.stack().hasNbt()) return false;
        var compound = info.stack().getOrCreateNbt();
        return compound.contains("is_owner") && compound.getBoolean("is_owner");
    }

    public static boolean isHandheld(ItemContextualModelInfo.ContextInfo info) {
        return info.mode() != ModelTransformationMode.GROUND && info.mode() != ModelTransformationMode.GUI;
    }

    // endregion

    // region BackEnd

    public static void init() {
        PVPRework.LOGGER.info("[{}] [Client]: Initialized Mod Item Models!", PVPRework.MOD_ID);
    }

    // endregion
}
