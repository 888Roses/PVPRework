package net.rose.pvp_rework.api.item.tint;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface TintedItem {
    TintData getColour(ItemStack stack, int layer);
}