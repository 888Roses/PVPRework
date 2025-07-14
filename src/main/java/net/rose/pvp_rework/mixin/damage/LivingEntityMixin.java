package net.rose.pvp_rework.mixin.damage;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.rose.pvp_rework.mixin_impl.damage.LivingEntityMixinImpl;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapMethod(method = "damage")
    private boolean damage$pvp_rework(DamageSource source, float amount, Operation<Boolean> original) {
        final var livingEntity = (LivingEntity) (Object) this;
        return LivingEntityMixinImpl.apply(livingEntity, source, amount, original);
    }
}
