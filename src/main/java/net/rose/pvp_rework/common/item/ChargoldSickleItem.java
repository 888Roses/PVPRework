package net.rose.pvp_rework.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.rose.pvp_rework.api.util.EnchantmentUtil;
import net.rose.pvp_rework.api.util.SoundUtil;
import net.rose.pvp_rework.api.util.ToolUtil;
import net.rose.pvp_rework.common.init.ModEnchantments;
import net.rose.pvp_rework.common.init.ModItems;
import net.rose.pvp_rework.common.init.ModSounds;

public class ChargoldSickleItem extends SwordItem {
    public static final float ATTACK_DAMAGE = 7F;
    protected static final float ATTACK_SPEED = 1.6F;

    public static final float AGILE_ATTACK_DAMAGE = 5F;
    protected static final float AGILE_ATTACK_SPEED = 1.9F;

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private final Multimap<EntityAttribute, EntityAttributeModifier> agileAttributeModifiers;

    public ChargoldSickleItem() {
        super(ToolMaterials.GOLD, ToolUtil.getAttackDamage(ToolMaterials.GOLD, (int) ATTACK_DAMAGE), ATTACK_SPEED,
                new Settings());

        this.attributeModifiers = createAttributes(ATTACK_DAMAGE, ATTACK_SPEED);
        this.agileAttributeModifiers = createAttributes(AGILE_ATTACK_DAMAGE, AGILE_ATTACK_SPEED);
    }

    private Multimap<EntityAttribute, EntityAttributeModifier> createAttributes(float damage, float attackSpeed) {
        final var builder = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(
                        ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                        damage - 1F, EntityAttributeModifier.Operation.ADDITION
                )
        );

        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(
                        ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                        ToolUtil.getEffectiveAttackSpeed(attackSpeed), EntityAttributeModifier.Operation.ADDITION
                )
        );

        return builder.build();
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getItemCooldownManager().isCoolingDown(ModItems.CHARGOLD_SICKLE)) {
            return ActionResult.PASS;
        }

        final var savedPos = entity.getPos();
        final var savedPitch = entity.getPitch();
        final var savedYaw = entity.getYaw();
        final var savedVelocity = entity.getVelocity();

        entity.updatePositionAndAngles(user.getX(), user.getY(), user.getZ(), user.getYaw(), user.getPitch());
        entity.setVelocity(user.getVelocity().multiply(-1));
        entity.velocityModified = true;

        user.updatePositionAndAngles(savedPos.x, savedPos.y, savedPos.z, savedYaw, savedPitch);
        user.setVelocity(savedVelocity.multiply(-1));
        user.velocityModified = true;

        SoundUtil.playSound(
                user.getWorld(), user.getPos(), ModSounds.CHARGOLD_SICKLE_SWAP, user.getSoundCategory(),
                0.6f, MathHelper.nextFloat(user.getRandom(), 0.95F, 1.05F)
        );

        final var hasAgile = EnchantmentUtil.hasEnchantment(stack, ModEnchantments.AGILE);
        user.getItemCooldownManager().set(ModItems.CHARGOLD_SICKLE, 20 * (hasAgile ? 3 : 5));

        return ActionResult.SUCCESS;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND && EnchantmentUtil.hasEnchantment(stack, ModEnchantments.AGILE)) {
            return this.agileAttributeModifiers;
        }

        return getAttributeModifiers(slot);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }

        return super.getAttributeModifiers(slot);
    }

    public Text getName(ItemStack stack) {
        return ((MutableText) super.getName(stack)).formatted(Formatting.YELLOW);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        SoundUtil.playSound(
                attacker.getWorld(), target.getPos(), ModSounds.CHARGOLD_SICKLE_ATTACK, attacker.getSoundCategory(),
                0.5F, MathHelper.nextFloat(attacker.getRandom(), 0.95F, 1.05F)
        );

        if (EnchantmentUtil.hasEnchantment(stack, ModEnchantments.AGILE)) {
            target.timeUntilRegen = Math.max(0, target.timeUntilRegen - 2);
        }

        return super.postHit(stack, target, attacker);
    }
}
