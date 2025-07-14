package net.rose.pvp_rework.api;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class API {
    public static String MOD_ID;

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static ModelIdentifier modelId(String path, String variant) {
        return new ModelIdentifier(MOD_ID, path, variant);
    }

    public static void setup(String modId) {
        MOD_ID = modId;
    }
}
