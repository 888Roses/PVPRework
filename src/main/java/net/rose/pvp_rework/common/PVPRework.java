package net.rose.pvp_rework.common;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.rose.pvp_rework.api.API;
import net.rose.pvp_rework.common.init.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PVPRework implements ModInitializer {
    public static final String MOD_NAME = "PVP Rework";
    public static final String MOD_ID = "pvp_rework";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        API.setup(MOD_ID);
        ModItems.init();
        ModEntityTypes.init();
        ModSounds.init();
        ModEnchantments.init();
        ModLootTableModifiers.init();
        ModParticles.init();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
