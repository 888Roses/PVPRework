package net.rose.pvp_rework.api.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.rose.pvp_rework.api.text.TextBuilder;

import java.util.function.Function;

public class CustomBookBuilder {
    public enum GenerationType {
        ORIGINAL,
        COPY,
        COPY_OF_COPY,
        TATTERED
    }

    // region Author
    private String author = "Unknown";

    public CustomBookBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    // endregion

    // region Title
    private String title;

    public CustomBookBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    // endregion

    // region Generation
    private int generation;

    public CustomBookBuilder withGeneration(GenerationType generation) {
        this.generation = generation.ordinal();
        return this;
    }

    // endregion

    // region Pages
    private final NbtList pages = new NbtList();

    public CustomBookBuilder withPage(Text text) {
        this.pages.add(NbtString.of(Text.Serializer.toJson(text)));
        return this;
    }

    public CustomBookBuilder withPage(Function<TextBuilder, Text> construct) {
        final var builder = new TextBuilder();
        return this.withPage(construct.apply(builder));
    }

    // endregion

    // region Build
    public ItemStack build(Item item) {
        final var stack = new ItemStack(item);
        final var compound = stack.getOrCreateNbt();
        compound.putString("title", this.title);
        compound.putString("author", this.author);
        compound.putInt("generation", this.generation);
        compound.putBoolean("resolved", true);
        compound.put("pages", this.pages);

        return stack;
    }

    public ItemStack build() {
        return this.build(Items.WRITTEN_BOOK);
    }

    // endregion
}