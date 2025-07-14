package net.rose.pvp_rework.mixin_impl.damage;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.rose.pvp_rework.common.networking.ResetVelocityNetworkMessageS2C;

/**
 * Removes the velocity freeze when taking damage.
 * Velocity freeze is especially bad when being inflicted with poison and trying to move around in midair.
 * This is also a problem with a leech trident (with 'enchancement') and trying to move airborne.
 */
public class LivingEntityMixinImpl {
    public static boolean apply(LivingEntity livingEntity, DamageSource source, float amount,
                                Operation<Boolean> original) {
        final var storedVelocity = livingEntity.getVelocity();
        final var result = original.call(source, amount);
        livingEntity.setVelocity(storedVelocity);

        if (livingEntity instanceof ServerPlayerEntity serverPlayerEntity) {
            ResetVelocityNetworkMessageS2C.send(serverPlayerEntity, storedVelocity);
        }

        return result;
    }
}
