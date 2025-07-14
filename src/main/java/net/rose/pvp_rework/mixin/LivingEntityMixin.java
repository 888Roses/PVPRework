package net.rose.pvp_rework.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "damage", at = @At("TAIL"))
    private void damage$pvprework(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        final var livingEntity = (LivingEntity) (Object) this;
        livingEntity.velocityModified = false;
    }
}