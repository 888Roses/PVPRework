package net.rose.pvp_rework.mixin.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.resource.ResourceReload;
import net.rose.pvp_rework.api.item.tint.TintedItemRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "onInitFinished", at = @At("TAIL"))
    private void onInitFinished$registerTintedItems(RealmsClient realms, ResourceReload reload,
                                                    RunArgs.QuickPlay quickPlay, CallbackInfo ci) {
        TintedItemRegistry.registerAll();
    }
}
