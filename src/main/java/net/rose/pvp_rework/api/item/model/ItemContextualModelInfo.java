package net.rose.pvp_rework.api.item.model;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ItemContextualModelInfo {
    public static final Map<Item, Map<ItemContext, String>> REGISTERED = new HashMap<>();

    public final Item item;
    public final Map<ItemContext, String> models = new HashMap<>();

    public static ItemContextualModelInfo create(Item item) {
        return new ItemContextualModelInfo(item);
    }

    private ItemContextualModelInfo(Item item) {
        this.item = item;
    }

    /**
     * Describes when to use the item model with the given {@code model} name based on the given {@code when}
     * {@link ItemContext}.
     *
     * @param model The name of the model in the {@code models/item} root directory. For instance, an item model at
     *              {@code assets/example_mod/models/item/dirt_handheld} would require a {@code model} name
     *              'dirt_handheld'.
     * @param when  Describes the conditions to use this model.
     * @return This {@link ItemContextualModelInfo}, for chaining.
     */
    public ItemContextualModelInfo with(String model, ItemContext when) {
        if (!this.models.containsKey(when)) {
            this.models.put(when, model);
        }

        return this;
    }

    /**
     * Registers this {@link ItemContextualModelInfo} in the {@link ItemContextualModelInfo#REGISTERED} list for use by
     * the {@link net.minecraft.client.render.item.ItemRenderer}.
     *
     * @return This {@link ItemContextualModelInfo} for saving purposes. Keep in mind that any other builder methods
     * (such as {@link ItemContextualModelInfo#with(String, ItemContext)} chained after this method will not have any
     * effect).
     * @apiNote Adding any other {@code register()} after an existing one will create duplicate model definitions and
     * create unexpected behaviours!
     */
    public ItemContextualModelInfo register() {
        REGISTERED.put(item, models);
        return this;
    }

    @FunctionalInterface
    public interface ItemContext {
        boolean isValid(ContextInfo info);
    }

    /**
     * Contains information about the current rendering context.
     *
     * @param stack        The {@link ItemStack} being rendered.
     * @param mode         Describes in what context the {@link ItemStack} is being rendered (in hand, in the gui,
     *                     etc.).
     * @param livingEntity The {@link LivingEntity} <b>possibly</b> holding that {@link ItemStack}. Keep in mind that
     *                     this {@link LivingEntity} <b>CAN BE NULL</b> in the case that the {@link ItemStack} is
     *                     rendered in the GUI for instance.
     */
    public record ContextInfo(ItemStack stack, ModelTransformationMode mode, @Nullable LivingEntity livingEntity) {
    }
}
