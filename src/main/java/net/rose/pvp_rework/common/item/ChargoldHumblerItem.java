package net.rose.pvp_rework.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rose.pvp_rework.api.util.ParticleUtil;
import net.rose.pvp_rework.api.util.ToolUtil;
import net.rose.pvp_rework.common.entity.ChargoldHumblerSpikeEntity;
import net.rose.pvp_rework.common.init.ModParticles;

import java.util.UUID;

public class ChargoldHumblerItem extends SwordItem {
    protected static final float ATTACK_SPEED = ToolUtil.getEffectiveAttackSpeed(1.1F);
    public static final float ATTACK_DAMAGE = 9F;
    public static final float ATTACK_REACH = 1F;

    protected static final UUID ATTACK_REACH_MODIFIER_ID = UUID.fromString("76a8dee3-3e7e-4e11-ba46-a19b0c724567");
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ChargoldHumblerItem() {
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

        builder.put(ReachEntityAttributes.ATTACK_RANGE,
                new EntityAttributeModifier(
                        ATTACK_REACH_MODIFIER_ID, "Weapon modifier",
                        ATTACK_REACH, EntityAttributeModifier.Operation.ADDITION
                )
        );

        this.attributeModifiers = builder.build();
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public Text getName(ItemStack stack) {
        return ((MutableText) super.getName(stack)).formatted(Formatting.YELLOW);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final var stack = user.getStackInHand(hand);

        var deltaTime = 0F;
        if (world.isClient()) deltaTime = MinecraftClient.getInstance().getTickDelta();
        var raycast = user.raycast(100, deltaTime, false);
        if (raycast.getType() == HitResult.Type.BLOCK) {
            var pos = raycast.getPos();

            final var count = 100;
            for (var i = 0; i < count; i++) {
                final var spread = (double)i/10d;
                final var relPos = pos.add((Math.random() - 0.5) * 2 * spread, 0, (Math.random() - 0.5) * 2 * spread);
                this.spawnHumbler(world, relPos, i/3);
                pos = pos.add(user.getRotationVector().withAxis(Direction.Axis.Y, 0).multiply(1.25));
            }

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }

    private void spawnHumbler(World world, Vec3d pos, int delay) {
        if (!world.isClient()) {
            final var entity = new ChargoldHumblerSpikeEntity(world, delay);
            entity.setDelay(delay);
            entity.updatePositionAndAngles(pos.x, pos.y, pos.z, world.getRandom().nextFloat() * 360f, 0F);
            world.spawnEntity(entity);
        }
    }
}