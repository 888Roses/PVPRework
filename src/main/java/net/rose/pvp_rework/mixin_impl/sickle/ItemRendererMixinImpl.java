package net.rose.pvp_rework.mixin_impl.sickle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.rose.pvp_rework.api.util.MatrixUtil;
import net.rose.pvp_rework.common.init.ModEntityComponents;
import net.rose.pvp_rework.common.init.ModItems;

public class ItemRendererMixinImpl {
    public static void renderItem(LivingEntity entity, boolean leftHanded, ModelTransformationMode renderMode,
                                  ItemStack item, MatrixStack matrices) {
        if (item.isEmpty() || !item.isOf(ModItems.CHARGOLD_SICKLE)) {
            return;
        }

        if (entity instanceof PlayerEntity player) {
            final var component = ModEntityComponents.CHARGOLD_SICKLE.get(player);
            final var deltaTick = MinecraftClient.getInstance().getTickDelta();
            final var progress = component.getCurrentAnimationProgress(deltaTick);
            final var rotationAmount = MathHelper.lerp(progress, 0, 360 * 2);
            final var offHand = leftHanded ? -1 : 1;

            if (renderMode.isFirstPerson()) {
                matrices.translate(0, 0.22f, 0);
                matrices.multiply(MatrixUtil.fromEulerAngles(-30f, 5, -130f * offHand));
                matrices.translate(0.05f * offHand, -0.07f, -0.25f);
            }

            matrices.multiply(MatrixUtil.fromEulerAngles(-rotationAmount, 0, 0));
        }
    }
}
