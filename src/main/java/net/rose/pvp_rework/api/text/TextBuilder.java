package net.rose.pvp_rework.api.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TextBuilder {
    private final List<Text> texts = new ArrayList<>();

    public TextBuilder text(Text text) {
        this.texts.add(text);
        return this;
    }

    public TextBuilder text(String text) {
        return this.text(Text.literal(text));
    }

    public TextBuilder text(String text, Style style) {
        return this.text(Text.literal(text).setStyle(style));
    }

    public TextBuilder translatable(String path, Object... args) {
        return this.text(Text.translatable(path, args));
    }

    public TextBuilder lineBreak() {
        return this.text("\n");
    }

    public MutableText build() {
        var result = Text.empty();

        for (var text : texts) {
            result = result.append(text);
        }

        return result;
    }
}
