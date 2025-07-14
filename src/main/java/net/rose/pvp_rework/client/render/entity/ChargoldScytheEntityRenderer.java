package net.rose.pvp_rework.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.rose.pvp_rework.common.entity.ChargoldScytheEntity;
import net.rose.pvp_rework.common.init.ModItems;

public class ChargoldScytheEntityRenderer extends EntityRenderer<ChargoldScytheEntity> {
    private static final ItemStack stack;
    private final ItemRenderer itemRenderer;

    public ChargoldScytheEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ChargoldScytheEntity disc, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F + MathHelper.lerp(tickDelta, disc.prevYaw, disc.getYaw())));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F + MathHelper.lerp(tickDelta, disc.prevPitch, disc.getPitch())));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(((float)disc.age + tickDelta) * -75.0F));
        matrices.scale(1.0F, 1.0F, 1.0F);
        matrices.translate(0, 0.25f, 0);

        this.itemRenderer.renderItem(
                stack, ModelTransformationMode.FIXED,
                light, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers,
                disc.getWorld(), disc.getId()
        );

        matrices.pop();
        super.render(disc, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    public Identifier getTexture(ChargoldScytheEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    static {
        stack = ModItems.CHARGOLD_SCYTHE.getDefaultStack();
    }
}
