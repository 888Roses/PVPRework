package net.rose.pvp_rework.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rose.pvp_rework.api.util.ParticleUtil;
import net.rose.pvp_rework.api.util.SoundUtil;
import net.rose.pvp_rework.common.init.ModEntityTypes;
import net.rose.pvp_rework.common.init.ModParticles;
import net.rose.pvp_rework.common.init.ModSounds;

public class ChargoldHumblerSpikeEntity extends Entity {
    public static final double RANDOM_ROTATION_RANGE = 16d;
    public static final double ANIMATION_DELAY = 7;
    public static final double ANIMATION_TIME = 2;

    public double previousAnimatedHeight, animatedHeight;
    public final double randomX, randomY, randomZ;
    private boolean hasDoneDamage;

    private static final TrackedData<Integer> DELAY = DataTracker.registerData(
            ChargoldHumblerSpikeEntity.class,
            TrackedDataHandlerRegistry.INTEGER
    );
    private final int initialDelay;

    public ChargoldHumblerSpikeEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
        this.initialDelay = 0;

        final var random = world.getRandom();
        this.randomX = MathHelper.nextDouble(random, -RANDOM_ROTATION_RANGE, RANDOM_ROTATION_RANGE);
        this.randomY = MathHelper.nextDouble(random, -RANDOM_ROTATION_RANGE, RANDOM_ROTATION_RANGE);
        this.randomZ = MathHelper.nextDouble(random, -RANDOM_ROTATION_RANGE, RANDOM_ROTATION_RANGE);
    }

    public ChargoldHumblerSpikeEntity(World world, int delay) {
        super(ModEntityTypes.CHARGOLD_HUMBLER_SPIKE, world);
        this.initialDelay = delay;

        final var random = world.getRandom();
        this.randomX = MathHelper.nextDouble(random, -RANDOM_ROTATION_RANGE, RANDOM_ROTATION_RANGE);
        this.randomY = MathHelper.nextDouble(random, -RANDOM_ROTATION_RANGE, RANDOM_ROTATION_RANGE);
        this.randomZ = MathHelper.nextDouble(random, -RANDOM_ROTATION_RANGE, RANDOM_ROTATION_RANGE);
    }

    public int getDelay() {
        return this.dataTracker.get(DELAY);
    }

    public void setDelay(int delay) {
        this.dataTracker.set(DELAY, delay);
    }

    @Override
    public void tick() {
        super.tick();
        final var delay = this.getDelay();
        if (this.age == delay) {
            ParticleUtil.spawnParticles(
                    this.getWorld(), ModParticles.CHARGOLD_HUMBLER_WARNING,
                    this.getPos().add(0, 0.1, 0), Vec3d.ZERO,
                    1, 0
            );
        }

        if (this.age == delay + ANIMATION_DELAY) {
            SoundUtil.playSound(
                    this.getWorld(), this.getPos(), ModSounds.CHARGOLD_HUMBLER_SPIKE, this.getSoundCategory(),
                    0.4f, MathHelper.nextFloat(this.getWorld().getRandom(), 0.9F, 1.1F)
            );
        }

        if (!this.hasDoneDamage && this.age >= delay + ANIMATION_DELAY) {
            final var entities = this.getWorld().getEntitiesByClass(
                    LivingEntity.class, this.getBoundingBox(),
                    e -> true
            );
            final var source = this.getWorld().getDamageSources().cactus();
            for (var entity : entities) {
                entity.timeUntilRegen = 0;
                if (entity.damage(source, 5f)) {
                    this.hasDoneDamage = true;
                }
            }
        }
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.PLAYERS;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(DELAY, this.initialDelay);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }
}
