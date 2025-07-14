package net.rose.pvp_rework.api.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TextUtil {
    public static MutableText getUseText() {
        return Text.translatable(MinecraftClient.getInstance().options.useKey.getTranslationKey());
    }
}
