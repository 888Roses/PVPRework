package net.rose.pvp_rework.common.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.rose.pvp_rework.api.component.ExtendedComponent;
import net.rose.pvp_rework.common.init.ModEntityComponents;

public class ChargoldScytheComponent implements AutoSyncedComponent, CommonTickingComponent,
        ExtendedComponent<ChargoldScytheComponent> {
    private LivingEntity livingEntity;
    private boolean hasScythe = true;

    public ChargoldScytheComponent(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    public boolean hasScythe() {
        return this.hasScythe;
    }

    public void setHasScythe(boolean hasScythe) {
        this.hasScythe = hasScythe;
        this.sync();
    }

    public void sync() {
        this.sync(this.livingEntity);
    }

    @Override
    public void tick() {
        if (livingEntity instanceof PlayerEntity player) {
            player.sendMessage(Text.literal("Has Scythe: " + hasScythe), true);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.hasScythe = nbtCompound.getBoolean("has_scythe");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putBoolean("has_scythe", this.hasScythe);
    }

    @Override
    public ComponentKey<ChargoldScytheComponent> getComponent() {
        return ModEntityComponents.CHARGOLD_SCYTHE;
    }
}