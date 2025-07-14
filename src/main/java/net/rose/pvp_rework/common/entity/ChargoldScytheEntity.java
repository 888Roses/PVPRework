package net.rose.pvp_rework.common.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rose.pvp_rework.api.util.SoundUtil;
import net.rose.pvp_rework.common.init.ModEntityComponents;
import net.rose.pvp_rework.common.init.ModSounds;
import net.rose.pvp_rework.common.networking.ChargoldScytheHitSoundNetworkMessageS2C;

import java.util.List;
import java.util.ArrayList;

public class ChargoldScytheEntity extends PersistentProjectileEntity {
    protected ItemStack stack;
    protected int lifetime = 40;
    protected float recallSpeed = 2.5F;

    protected final List<Vec3d> HIT_ENEMY_SOUNDS = new ArrayList<>();
    protected int nextEnemySoundAge;

    public ChargoldScytheEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public float getRecallSpeed() {
        return recallSpeed;
    }

    public void setRecallSpeed(float recallSpeed) {
        this.recallSpeed = recallSpeed;
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.inGroundTime > 5) {
            this.returnScythe();
            this.discard();
            return;
        }

        if (this.getOwner() instanceof ServerPlayerEntity serverPlayer) {
            if (!this.HIT_ENEMY_SOUNDS.isEmpty() && this.age >= this.nextEnemySoundAge) {
                final var item = this.HIT_ENEMY_SOUNDS.get(this.HIT_ENEMY_SOUNDS.size() - 1);
                this.HIT_ENEMY_SOUNDS.remove(this.HIT_ENEMY_SOUNDS.size() - 1);
                this.nextEnemySoundAge = this.age + 1;
                ChargoldScytheHitSoundNetworkMessageS2C.send(serverPlayer, item);
            }
        }

        if ((this.age + 2) % 5 == 0) {
            SoundUtil.playSoundWithDistance(
                    this.getWorld(), this.getPos(),
                    ModSounds.CHARGOLD_SCYTHE_TRAVEL, SoundCategory.PLAYERS,
                    MathHelper.nextFloat(this.random, 0.95F, 1.05F) - 0.25F,
                    MathHelper.nextFloat(this.random, 0.95F, 1.2F),
                    50F
            );
        }

        if (this.age > this.lifetime) {
            if (this.getOwner() == null) {
                this.discard();
                return;
            }

            final var direction = this.getOwner().getEyePos().subtract(0, 0.1d, 0).subtract(this.getPos());
            // This checks if the scythe is close to its owner, in which case we want to discard it because the player
            // would have picked it up. We also want to reset the "hasScythe" flag in the component.
            if (direction.length() < this.getVelocity().length() / 2) {
                this.returnScythe();
                this.discard();
                return;
            }

            this.setVelocity(direction.normalize().multiply(this.recallSpeed));
        }

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            final var box = Box.of(this.getPos(), this.getWidth(), this.getHeight(), this.getWidth());
            final var entitiesInRadius = this.getWorld().getEntitiesByClass(
                    LivingEntity.class, box,
                    entity -> entity != this.getOwner()
            );

            if (entitiesInRadius.isEmpty()) {
                return;
            }

            var damageSource = serverWorld.getDamageSources().generic();
            if (this.getOwner() instanceof LivingEntity livingEntity) {
                damageSource = serverWorld.getDamageSources().mobAttack(livingEntity);
                if (livingEntity instanceof PlayerEntity player) {
                    damageSource = serverWorld.getDamageSources().playerAttack(player);
                }
            }

            var amount = 9F;
            if (this.getOwner() instanceof LivingEntity ownerLivingEntity) {
                amount = (float) ownerLivingEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            }

            for (var livingEntity : entitiesInRadius) {
                if (livingEntity.damage(damageSource, amount)) {
                    this.HIT_ENEMY_SOUNDS.add(livingEntity.getPos());

                    if (getOwner() instanceof LivingEntity ownerLivingEntity) {
                        EnchantmentHelper.onUserDamaged(livingEntity, ownerLivingEntity);
                        EnchantmentHelper.onTargetDamaged(ownerLivingEntity, livingEntity);
                    }
                }
            }
        }
    }

    private void returnScythe() {
        if (this.getOwner() instanceof LivingEntity ownerLivingEntity) {
            final var component = ModEntityComponents.CHARGOLD_SCYTHE.get(ownerLivingEntity);
            component.setHasScythe(true);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        final var direction = blockHitResult.getSide();
        this.setPosition(this.getPos().subtract(this.getVelocity()));
        switch (direction.getAxis()) {
            case X -> this.setVelocity(this.getVelocity().multiply(-1.0, 1.0, 1.0));
            case Y -> this.setVelocity(this.getVelocity().multiply(1.0, -1.0, 1.0));
            case Z -> this.setVelocity(this.getVelocity().multiply(1.0, 1.0, -1.0));
        }

        SoundUtil.playSoundWithDistance(
                this.getWorld(), this.getPos(),
                ModSounds.CHARGOLD_SCYTHE_BOUNCE, SoundCategory.PLAYERS,
                0.9F, MathHelper.nextFloat(random, 0.8F, 1.1F),
                40F
        );
    }

    @Override
    public void setPierceLevel(byte level) {
        super.setPierceLevel(level);
    }
}
