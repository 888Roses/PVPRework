package net.rose.pvp_rework.api.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentUtil {
    public static EnchantmentBuilder builder() {
        return new EnchantmentBuilder();
    }

    public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment) {
        return EnchantmentHelper.getLevel(enchantment, stack) > 0;
    }

    public static class EnchantmentBuilder {
        private final Map<Enchantment, Integer> enchantments = new HashMap<>();

        private EnchantmentBuilder() {}

        public EnchantmentBuilder add(Enchantment enchantment, int level) {
            this.enchantments.put(enchantment, level);
            return this;
        }

        public EnchantmentBuilder add(Enchantment enchantment) {
            return add(enchantment, 1);
        }

        public Map<Enchantment, Integer> toMap() {
            return this.enchantments;
        }

        public ItemStack enchant(ItemStack stack) {
            EnchantmentHelper.set(this.toMap(), stack);
            return stack;
        }
    }
}
