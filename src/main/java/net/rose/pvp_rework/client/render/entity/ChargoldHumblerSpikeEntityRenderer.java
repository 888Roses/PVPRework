package net.rose.pvp_rework.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.rose.pvp_rework.api.util.MatrixUtil;
import net.rose.pvp_rework.common.entity.ChargoldHumblerSpikeEntity;
import net.rose.pvp_rework.common.init.ModItems;

public class ChargoldHumblerSpikeEntityRenderer extends EntityRenderer<ChargoldHumblerSpikeEntity> {
    private final ItemRenderer itemRenderer;

    public ChargoldHumblerSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ChargoldHumblerSpikeEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        final var animationTime = ChargoldHumblerSpikeEntity.ANIMATION_TIME;
        final var delay = ChargoldHumblerSpikeEntity.ANIMATION_DELAY;

        entity.previousAnimatedHeight = entity.animatedHeight;
        final var progress = ((double) entity.age - (double) (entity.getDelay() + delay)) / (double) (animationTime);
        final var clampedProgress = MathHelper.clamp(progress, 0d, 1d);
        entity.animatedHeight = MathHelper.lerp(clampedProgress, -2d, 0.7d + (entity.getYaw() / 360) * 0.2);

        final var effectiveHeight = MathHelper.lerp(tickDelta, entity.previousAnimatedHeight, entity.animatedHeight);
        matrices.translate(0, effectiveHeight, 0);

        matrices.multiply(MatrixUtil.fromEulerAngles(0, yaw, 0));
        matrices.multiply(MatrixUtil.fromEulerAngles(0, 0, 45));
        matrices.multiply(MatrixUtil.fromEulerAngles(entity.randomX, entity.randomY, entity.randomZ));

        this.itemRenderer.renderItem(
                new ItemStack(ModItems.CHARGOLD_HUMBLER), ModelTransformationMode.FIXED,
                light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,
                entity.getWorld(), entity.getId()
        );

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ChargoldHumblerSpikeEntity entity) {
        return PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;
    }
}
