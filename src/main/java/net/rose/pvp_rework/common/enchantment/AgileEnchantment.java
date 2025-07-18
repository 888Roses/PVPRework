package net.rose.pvp_rework.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.rose.pvp_rework.common.item.ChargoldSickleItem;

public class AgileEnchantment extends Enchantment {
    public AgileEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ChargoldSickleItem;
    }
}
