package net.rose.pvp_rework.mixin.api;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.rose.pvp_rework.api.stance.CustomStance;
import net.rose.pvp_rework.api.stance.CustomStanceRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {
    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    private <T extends LivingEntity> void positionRightArm$setCustomStance(T livingEntity, CallbackInfo callbackInfo) {
        var isValidStance = false;
        // noinspection unchecked
        var model = (BipedEntityModel<T>) (Object) this;
        for (var pose : CustomStanceRegistry.REGISTERED) {
            if (!pose.matches(livingEntity)) continue;

            pose.apply(model, livingEntity, CustomStance.StanceType.SET_RIGHT_ARM);
            isValidStance = true;
        }

        if (isValidStance) callbackInfo.cancel();
    }

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    private <T extends LivingEntity> void positionLeftArm$setCustomStance(T livingEntity, CallbackInfo callbackInfo) {
        var isValidStance = false;
        // noinspection unchecked
        var model = (BipedEntityModel<T>) (Object) this;
        for (var pose : CustomStanceRegistry.REGISTERED) {
            if (!pose.matches(livingEntity)) continue;

            pose.apply(model, livingEntity, CustomStance.StanceType.SET_LEFT_ARM);
            isValidStance = true;
        }

        if (isValidStance) callbackInfo.cancel();
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private <T extends LivingEntity> void setAngles$setCustomStance$Head(
            T livingEntity,
            float limbAngle, float limbDistance,
            float animationProgress,
            float headYaw, float headPitch,
            CallbackInfo callbackInfo
    ) {
        // noinspection unchecked
        var model = (BipedEntityModel<T>) (Object) this;
        for (var pose : CustomStanceRegistry.REGISTERED) {
            if (!pose.matches(livingEntity)) continue;
            pose.apply(model, livingEntity, CustomStance.StanceType.SET_BODY_HEAD);
        }
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private <T extends LivingEntity> void setAngles$setCustomStance$Tail(
            T livingEntity,
            float limbAngle, float limbDistance,
            float animationProgress,
            float headYaw, float headPitch,
            CallbackInfo callbackInfo
    ) {
        // noinspection unchecked
        var model = (BipedEntityModel<T>) (Object) this;
        for (var pose : CustomStanceRegistry.REGISTERED) {
            if (!pose.matches(livingEntity)) continue;
            pose.apply(model, livingEntity, CustomStance.StanceType.SET_BODY_TAIL);
        }
    }
}
