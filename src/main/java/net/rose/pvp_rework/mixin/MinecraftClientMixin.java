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
    public @Nullable @Shadow ClientPlayerEntity player;

    @Inject(
            at = @At("HEAD"),
            method = "doAttack",
            cancellable = true
    )
    private void amarite$cancelAttack(CallbackInfoReturnable<Boolean> cir) {
        if (this.player != null && this.player.getMainHandStack().isOf(ModItems.CHARGOLD_SCYTHE)) {
            final var component = ModEntityComponents.CHARGOLD_SCYTHE.get(this.player);
            if (!component.hasScythe()) {
                cir.setReturnValue(true);
            }
        }
    }
}
