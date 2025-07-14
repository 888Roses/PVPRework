package net.rose.pvp_rework.api.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemUtil {
    public static void decrementUnlessCreative(ItemStack stack, int amount, @Nullable LivingEntity entity) {
        if (entity == null || !EntityUtil.isInCreativeMode(entity)) {
            stack.decrement(amount);
        }
    }
}