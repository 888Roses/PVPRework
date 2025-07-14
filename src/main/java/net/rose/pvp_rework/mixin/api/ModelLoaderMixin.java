package net.rose.pvp_rework.mixin.api;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.rose.pvp_rework.api.API;
import net.rose.pvp_rework.api.item.model.ItemContextualModelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
                    ordinal = 3,
                    shift = At.Shift.AFTER
            )
    )
    public void addModel$registerCustomModels(
            BlockColors blockColors, Profiler profiler,
            Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
            Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates,
            CallbackInfo callbackInfo
    ) {
        for (var models : ItemContextualModelInfo.REGISTERED.entrySet())
            for (var model : models.getValue().entrySet())
                this.addModel(API.modelId(model.getValue(), "inventory"));
    }
}