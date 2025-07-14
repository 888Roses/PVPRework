package net.rose.pvp_rework.mixin.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private static DamageType previousDamageType;
    @Unique
    private static float currentAmount;
    @Unique
    private static long lastDamageTime;

    @Inject(
            method = "damage",
            at = @At("TAIL")
    )
    private void damage$modifyDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getAttacker() instanceof PlayerEntity player && player.isCreative()) {
            var currentTime = source.getAttacker().getWorld().getTime();
            if (currentTime - lastDamageTime < 5) {
                var type = source.getType();
                if (previousDamageType != type) currentAmount = amount;
                else currentAmount += amount;
                previousDamageType = type;
            }
            else currentAmount = amount;
            lastDamageTime = currentTime;

            player.sendMessage(Text.literal(((float) Math.round(currentAmount * 10F) / 10F) + " ❤").formatted(Formatting.RED), true);
        }
    }
}
