package net.rose.pvp_rework.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ChargoldSparkParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    private final Vec3d initialPos;
    private final float desiredScale;

    public ChargoldSparkParticle(ClientWorld clientWorld,
                                 double posX, double posY, double posZ,
                                 double velX, double velY, double velZ,
                                 SpriteProvider spriteProvider) {
        super(clientWorld, posX, posY, posZ);
        this.velocityMultiplier = 1F;
        this.ascending = true;
        this.spriteProvider = spriteProvider;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
        setColor(1f, 1f, 1f);
        setVelocity(velX * 0.25, velY * 0.25, velZ * 0.25);
        setMaxAge(clientWorld.random.nextInt(4) + 2);
        this.initialPos = new Vec3d(posX, posY, posZ);
        this.desiredScale = MathHelper.nextFloat(clientWorld.random, 0.6F, 1F);
        this.gravityStrength = 2F;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
        this.scale = desiredScale * (1f - (float) this.age / (float) this.maxAge);
        this.alpha = (float) this.age / (float) this.maxAge;
        this.velocityX *= 0.7F;
        this.velocityY *= 0.7F;
        this.velocityZ *= 0.7F;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        final var posX = (float) MathHelper.lerp(tickDelta, this.prevPosX, this.x);
        final var posY = (float) MathHelper.lerp(tickDelta, this.prevPosY, this.y);
        final var posZ = (float) MathHelper.lerp(tickDelta, this.prevPosZ, this.z);
        var rotation = new Quaternionf().lookAlong(
                new Vec3d(posX, posY, posZ).normalize().toVector3f(),
                initialPos.toVector3f().normalize()
        );

        var vertices = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)
        };
        var size = this.getSize(tickDelta);

        final var cameraPosition = camera.getPos();
        final var relativePosX = posX - (float) cameraPosition.getX();
        final var relativePosY = posY - (float) cameraPosition.getY();
        final var relativePosZ = posZ - (float) cameraPosition.getZ();
        for (var j = 0; j < 4; j++) {
            var vector3f = vertices[j];
            vector3f.rotate(rotation);
            vector3f.mul(size);
            vector3f.add(relativePosX, relativePosY, relativePosZ);
        }

        var k = this.getMinU();
        var l = this.getMaxU();
        var m = this.getMinV();
        var n = this.getMaxV();
        var o = this.getBrightness(tickDelta);
        vertexConsumer.vertex(vertices[0].x(), vertices[0].y(), vertices[0].z()).texture(l, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vertices[1].x(), vertices[1].y(), vertices[1].z()).texture(l, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vertices[2].x(), vertices[2].y(), vertices[2].z()).texture(k, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vertices[3].x(), vertices[3].y(), vertices[3].z()).texture(k, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();

        vertexConsumer.vertex(vertices[3].x(), vertices[3].y(), vertices[3].z()).texture(k, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vertices[2].x(), vertices[2].y(), vertices[2].z()).texture(k, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vertices[1].x(), vertices[1].y(), vertices[1].z()).texture(l, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vertices[0].x(), vertices[0].y(), vertices[0].z()).texture(l, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType particleEffect, ClientWorld clientWorld,
                                       double posX, double posY, double posZ,
                                       double velX, double velY, double velZ) {
            return new ChargoldSparkParticle(clientWorld, posX, posY, posZ, velX, velY, velZ, this.spriteProvider);
        }
    }
}
