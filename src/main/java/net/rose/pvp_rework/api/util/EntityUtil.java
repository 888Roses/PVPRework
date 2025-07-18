package net.rose.pvp_rework.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

public class EntityUtil {
    public static boolean isInCreativeMode(LivingEntity livingEntity) {
        return livingEntity instanceof PlayerEntity player && player.isCreative();
    }

    public static void throwProjectile(
            ProjectileEntity projectile, Entity owner,
            float roll, float power, float divergence,
            boolean setPitch, boolean setYaw, boolean setPosition
    ) {
        projectile.setOwner(owner);
        projectile.setPosition(owner.getEyePos());
        projectile.setPitch(owner.getPitch());
        projectile.setYaw(owner.getYaw());
        projectile.setVelocity(owner, owner.getPitch(), owner.getYaw(), roll, power, divergence);
    }

    public static void throwProjectile(
            ProjectileEntity projectile, Entity owner,
            float roll, float power, float divergence
    ) {
        throwProjectile(projectile, owner, roll, power, divergence, true, true, true);
    }

    public static void throwProjectile(
            ProjectileEntity projectile, Entity owner,
            float power, float divergence
    ) {
        throwProjectile(projectile, owner, 0, power, divergence);
    }

    public static void throwProjectile(
            ProjectileEntity projectile, Entity owner,
            float power
    ) {
        throwProjectile(projectile, owner, power, 1F);
    }

    /**
     * Modifies the velocity of the given entity accordingly to this rule: if the signum of the current velocity of the
     * entity on an axis is different from the signum of the given velocity on that same axis, set the velocity of the
     * entity on that axis to the given velocity. Otherwise, add the given velocity to the entity's current velocity.
     *
     * @param entity   The entity affected by that velocity change.
     * @param velocity A {@link Vec3d} representing the added or set velocity on the X, Y and Z axis.
     * @return The effective velocity, that is the velocity set to the entity after the condition is checked. Each axis
     * contains either the axis value of the given velocity if the signums were different, or the velocity of the entity
     * on that axis, added the value of the axis of the given velocity if the signums were equal.
     */
    public static Vec3d addOrSetVelocity(Entity entity, Vec3d velocity) {
        final var currentVelocity = entity.getVelocity();
        final var effectiveVelocity = new Vec3d(
                Math.signum(currentVelocity.x) != Math.signum(velocity.x) ? velocity.x : currentVelocity.x + velocity.x,
                Math.signum(currentVelocity.y) != Math.signum(velocity.y) ? velocity.y : currentVelocity.y + velocity.y,
                Math.signum(currentVelocity.z) != Math.signum(velocity.z) ? velocity.z : currentVelocity.z + velocity.z
        );

        entity.setVelocity(effectiveVelocity);
        return effectiveVelocity;
    }
}
