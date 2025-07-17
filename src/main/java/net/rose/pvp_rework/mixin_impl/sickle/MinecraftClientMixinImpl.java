package net.rose.pvp_rework.mixin_impl.sickle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.rose.pvp_rework.common.init.ModItems;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class MinecraftClientMixinImpl {
    public static boolean lastUsedHand;

    public static void doAttack$pvp_rework(CallbackInfoReturnable<Boolean> cir, HitResult crosshairTarget,
                                           PlayerEntity player) {
        if (player == null) {
            return;
        }

        if (!player.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.CHARGOLD_SICKLE) ||
                !player.getStackInHand(Hand.OFF_HAND).isOf(ModItems.CHARGOLD_SICKLE)) {
            return;
        }

        if (!player.handSwinging || player.handSwingTicks >= 3 || player.handSwingTicks < 0) {
            lastUsedHand = !lastUsedHand;
        }

        player.swingHand(lastUsedHand ? Hand.MAIN_HAND : Hand.OFF_HAND);
        cir.setReturnValue(false);
    }
}
