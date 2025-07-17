package net.rose.pvp_rework.mixin.sickle;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.rose.pvp_rework.common.init.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true, index = 7)
    private float tickHandSwing$pvp_rework(float value, AbstractClientPlayerEntity player, float tickDelta,
                                           float pitch, Hand hand, float swingProgress, ItemStack item,
                                           float equipProgress, MatrixStack matrices,
                                           VertexConsumerProvider vertexConsumers, int light) {
        if (item.isEmpty() || !item.isOf(ModItems.CHARGOLD_SICKLE)) return value;
        return 0F;
    }
}