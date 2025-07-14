package net.rose.pvp_rework.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.rose.pvp_rework.api.util.ToolUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ChargoldScytheItem extends AxeItem {
    protected static final UUID ATTACK_REACH_MODIFIER_ID = UUID.fromString("76a8dee3-3e7e-4e11-ba46-a19b0c724567");
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    protected static final float ATTACK_SPEED = ToolUtil.getEffectiveAttackSpeed(1.3F);

    public ChargoldScytheItem() {
        super(ToolMaterials.GOLD, ToolUtil.getAttackDamage(ToolMaterials.GOLD, 9), ATTACK_SPEED, new Settings());

        final var builder = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(
                        ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                        8F, EntityAttributeModifier.Operation.ADDITION
                )
        );

        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(
                        ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                        ATTACK_SPEED, EntityAttributeModifier.Operation.ADDITION
                )
        );

        builder.put(ReachEntityAttributes.ATTACK_RANGE,
                new EntityAttributeModifier(
                        ATTACK_REACH_MODIFIER_ID, "Weapon modifier",
                        0.5F, EntityAttributeModifier.Operation.ADDITION
                )
        );

        this.attributeModifiers = builder.build();
    }

    @Override
    public Text getName(ItemStack stack) {
        return ((MutableText) super.getName(stack)).formatted(Formatting.YELLOW);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        final var options = MinecraftClient.getInstance().options;
        final var keyText = Text.translatable(options.useKey.getTranslationKey()).formatted(Formatting.GOLD);
        tooltip.add(Text.translatable(stack.getTranslationKey() + ".desc1", keyText).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable(stack.getTranslationKey() + ".desc2").formatted(Formatting.GRAY));
        tooltip.add(Text.literal(" "));
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }
}
