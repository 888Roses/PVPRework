package net.rose.pvp_rework.api.item.tint;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TintedItemRegistry {
    public static Map<Item, TintedItem> REGISTRY = new HashMap<>();

    public static TintedItem register(Item item, TintedItem tintedItem) {
        REGISTRY.put(item, tintedItem);
        return tintedItem;
    }

    public static TintData getTintData(ItemStack itemstack, int layer) {
        if (!REGISTRY.containsKey(itemstack.getItem())) return TintData.noTint();
        var tintedItem = REGISTRY.get(itemstack.getItem());
        return tintedItem.getColour(itemstack, layer);
    }

    public static Optional<Integer> getTint(ItemStack itemStack, int layer) {
        var tintData = getTintData(itemStack, layer);
        return tintData.hasTint() ? Optional.of(tintData.getTint()) : Optional.empty();
    }

    public static void registerAll() {
        for (var item : REGISTRY.entrySet()) {
            MinecraftClient.getInstance().itemColors.register((stack, layer) ->
                    getTint(stack, layer).orElse(-1), item.getKey());
        }
    }
}
