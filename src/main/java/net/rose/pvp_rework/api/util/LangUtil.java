package net.rose.pvp_rework.api.util;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;

public class LangUtil {
    private final FabricLanguageProvider.TranslationBuilder lang;

    public LangUtil(FabricLanguageProvider.TranslationBuilder lang) {
        this.lang = lang;
    }

    public static String withPath(Item item, String additionalPath) {
        return item.getTranslationKey() + additionalPath;
    }

    public void add(SoundEvent sound, String subtitle) {
        lang.add(sound.getId().toTranslationKey("subtitles"), subtitle);
    }

    public void add(Item item, String after, String key) {
        var effectivePath = withPath(item, after);
        lang.add(effectivePath, key);
    }

    public void addDamageType(String damageType, String key) {
        lang.add(damageType, key);
    }

    public void add(Enchantment enchantment, String name, String description) {
        lang.add(enchantment, name);
        lang.add(enchantment.getTranslationKey() + ".desc", description);
    }
}
