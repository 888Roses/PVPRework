package net.rose.pvp_rework.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.rose.pvp_rework.api.util.EnchantmentUtil;
import net.rose.pvp_rework.api.util.SoundUtil;
import net.rose.pvp_rework.api.util.ToolUtil;
import net.rose.pvp_rework.common.init.ModEnchantments;
import net.rose.pvp_rework.common.init.ModEntityComponents;
import net.rose.pvp_rework.common.init.ModEntityTypes;
import net.rose.pvp_rework.common.init.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ChargoldScytheItem extends AxeItem {
    protected static final UUID ATTACK_REACH_MODIFIER_ID = UUID.fromString("76a8dee3-3e7e-4e11-ba46-a19b0c724567");
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    protected static final float ATTACK_SPEED = ToolUtil.getEffectiveAttackSpeed(1.3F);
    public static final float TRAVEL_SPEED = 2.25F;

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

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final var component = ModEntityComponents.CHARGOLD_SCYTHE.get(user);
        final var stack = user.getStackInHand(hand);

        if (!component.hasScythe()) {
            final var scytheEntity = component.getScytheEntity();
            if (EnchantmentUtil.hasEnchantment(stack, ModEnchantments.RECALL) && scytheEntity != null) {
                // Recalls it.
                scytheEntity.setLifetime(-1);
                return TypedActionResult.success(stack);
            }

            return TypedActionResult.pass(stack);
        }

        if (world instanceof
                ServerWorld serverWorld) {
            final var scytheEntity = ModEntityTypes.CHARGOLD_SCYTHE.create(world);
            if (scytheEntity != null) {
                scytheEntity.setOwner(user);
                scytheEntity.setNoGravity(true);
                scytheEntity.setLifetime(25);
                scytheEntity.setRecallSpeed(TRAVEL_SPEED);
                scytheEntity.setStack(stack.copy());
                scytheEntity.refreshPositionAndAngles(user.getX(), user.getEyeY() - 0.1, user.getZ(), user.getYaw(), user.getPitch());
                scytheEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, TRAVEL_SPEED, 0.0F);
                component.setHasScythe(false);
                component.setScythe(scytheEntity);

                serverWorld.spawnEntity(scytheEntity);
                user.swingHand(hand);
            }
        }

        SoundUtil.playSound(
                world, user.getPos(), ModSounds.CHARGOLD_SCYTHE_THROW, user.getSoundCategory(),
                0.9f, MathHelper.nextFloat(world.random, 0.95f, 1.1f)
        );

        return TypedActionResult.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity livingEntity) {
            final var component = ModEntityComponents.CHARGOLD_SCYTHE.get(livingEntity);

            if (!stack.hasNbt()
                    || !stack.getOrCreateNbt().contains("has_scythe")
                    || stack.getOrCreateNbt().getBoolean("has_scythe") != component.hasScythe()) {
                stack.getOrCreateNbt().putBoolean("has_scythe", component.hasScythe());
            }
        }
    }

    @Override
    public ItemStack getDefaultStack() {
        var stack = super.getDefaultStack();
        stack.getOrCreateNbt().putBoolean("has_scythe", true);
        return stack;
    }
}
