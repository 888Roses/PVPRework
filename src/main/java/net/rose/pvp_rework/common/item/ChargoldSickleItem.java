package net.rose.pvp_rework.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.rose.pvp_rework.api.util.ToolUtil;

public class ChargoldSickleItem extends SwordItem {
    protected static final float ATTACK_SPEED = ToolUtil.getEffectiveAttackSpeed(1.6F);
    public static final float ATTACK_DAMAGE = 5F;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ChargoldSickleItem() {
        super(ToolMaterials.GOLD, ToolUtil.getAttackDamage(ToolMaterials.GOLD, (int) ATTACK_DAMAGE), ATTACK_SPEED,
                new Settings());

        final var builder = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(
                        ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                        ATTACK_DAMAGE - 1F, EntityAttributeModifier.Operation.ADDITION
                )
        );

        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(
                        ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                        ATTACK_SPEED, EntityAttributeModifier.Operation.ADDITION
                )
        );

        this.attributeModifiers = builder.build();
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    public Text getName(ItemStack stack) {
        return ((MutableText) super.getName(stack)).formatted(Formatting.YELLOW);
    }
}
