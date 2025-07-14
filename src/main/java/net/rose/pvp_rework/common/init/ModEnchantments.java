package net.rose.pvp_rework.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.enchantment.RecallEnchantment;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModEnchantments {
    private static final Map<Enchantment, Identifier> ENCHANTMENTS = new LinkedHashMap<>();

    public static final Enchantment RECALL = createEnchantment("recall", new RecallEnchantment());

    // region backend

    public static void init() {
        ENCHANTMENTS.keySet().forEach(ModEnchantments::register);
        PVPRework.LOGGER.info("[{}]: Mod Enchantments Initialized!", PVPRework.MOD_ID);
    }

    private static void register(Enchantment enchantment) {
        Registry.register(Registries.ENCHANTMENT, ENCHANTMENTS.get(enchantment), enchantment);
    }

    private static <T extends Enchantment> T createEnchantment(@SuppressWarnings("SameParameterValue") String name,
                                                               T enchantment) {
        ENCHANTMENTS.put(enchantment, new Identifier(PVPRework.MOD_ID, name));
        return enchantment;
    }

    // endregion
}
