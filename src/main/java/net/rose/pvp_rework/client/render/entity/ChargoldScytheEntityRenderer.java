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
import net.minecraft.util.math.Vec3d;
import net.rose.pvp_rework.api.util.MatrixUtil;
import net.rose.pvp_rework.common.entity.ChargoldScytheEntity;
import net.rose.pvp_rework.common.init.ModItems;
import org.joml.Vector3d;

public class ChargoldScytheEntityRenderer extends EntityRenderer<ChargoldScytheEntity> {
    private static final ItemStack stack;
    private final ItemRenderer itemRenderer;

    public ChargoldScytheEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ChargoldScytheEntity scythe, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(MatrixUtil.fromEulerAngles(0, ((float) scythe.age + tickDelta) * -75.0F, 0));
        matrices.multiply(MatrixUtil.fromEulerAngles(90.0F, 180.0F, 0));

        matrices.scale(1.2F, 1.2F, 1.2F);
        matrices.translate(0, 0.25f, 0);

        this.itemRenderer.renderItem(
                scythe.getStack() == null ? stack : scythe.getStack(), ModelTransformationMode.FIXED,
                light, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers,
                scythe.getWorld(), scythe.getId()
        );

        matrices.pop();
        super.render(scythe, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    public Identifier getTexture(ChargoldScytheEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    static {
        stack = ModItems.CHARGOLD_SCYTHE.getDefaultStack();
    }
}
