package net.rose.pvp_rework.mixin.api;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.rose.pvp_rework.api.API;
import net.rose.pvp_rework.api.item.model.ItemContextualModelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    public abstract ItemModels getModels();

    @Shadow
    public abstract void renderItem(
            ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, 
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, 
            int light, int overlay, BakedModel model
    );

    @Inject(
            method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"),
            cancellable = true
    )
    private void renderItem$renderCustomModel(
            LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            World world, int light, int overlay, int seed, CallbackInfo callbackInfo
    ) {
        var bakedModel = getRegisteredModel(stack, renderMode, entity);
        if (bakedModel == null) return;

        renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, bakedModel);
        callbackInfo.cancel();
    }

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private BakedModel renderItem$useCustomModel(
            BakedModel value,
            ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, int overlay,
            BakedModel model
    ) {
        var bakedModel = getRegisteredModel(stack, renderMode, null);
        return bakedModel == null ? model : bakedModel;
    }

    @Unique
    private BakedModel getRegisteredModel(ItemStack stack, ModelTransformationMode renderMode, LivingEntity entity) {
        for (var models : ItemContextualModelInfo.REGISTERED.entrySet()) {
            if (!stack.isOf(models.getKey())) continue;

            for (var model : models.getValue().entrySet()) {
                if (!model.getKey().isValid(new ItemContextualModelInfo.ContextInfo(stack, renderMode, entity))) {
                    continue;
                }

                return this.getModels().getModelManager().getModel(API.modelId(model.getValue(), "inventory"));
            }
        }

        return null;
    }
}