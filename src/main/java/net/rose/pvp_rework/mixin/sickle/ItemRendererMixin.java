package net.rose.pvp_rework.mixin.sickle;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.rose.pvp_rework.mixin_impl.sickle.ItemRendererMixinImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(
            at = @At("HEAD"),
            method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;" +
                    "Lnet/minecraft/client/render/model/json/ModelTransformationMode;" +
                    "ZLnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V"
    )
    private void renderItem(LivingEntity entity, ItemStack item, ModelTransformationMode renderMode,
                            boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                            World world, int light, int overlay, int seed, CallbackInfo ci) {
        ItemRendererMixinImpl.renderItem(entity, leftHanded, renderMode, item, matrices);
    }
}
