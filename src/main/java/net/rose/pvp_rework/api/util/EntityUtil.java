package net.rose.pvp_rework.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;

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
}
