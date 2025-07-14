package net.rose.pvp_rework.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.rose.pvp_rework.common.init.ModEntityComponents;
import net.rose.pvp_rework.common.init.ModItems;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void cancelAttack$pvp_rework(CallbackInfoReturnable<Boolean> cir) {
        if (this.player == null) return;

        final var component = ModEntityComponents.CHARGOLD_SCYTHE.get(this.player);
        final var stack = this.player.getMainHandStack();
        if (stack.isOf(ModItems.CHARGOLD_SCYTHE) && component.isThrown()) {
            cir.setReturnValue(true);
        }
    }
}
