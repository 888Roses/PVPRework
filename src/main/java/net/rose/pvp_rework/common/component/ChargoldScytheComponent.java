package net.rose.pvp_rework.common.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.rose.pvp_rework.api.component.ExtendedComponent;
import net.rose.pvp_rework.common.entity.ChargoldScytheEntity;
import net.rose.pvp_rework.common.init.ModEntityComponents;

public class ChargoldScytheComponent implements AutoSyncedComponent,
        ExtendedComponent<ChargoldScytheComponent> {
    private LivingEntity livingEntity;
    private boolean hasScythe = true;
    private ChargoldScytheEntity scytheEntity;

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

    public ChargoldScytheEntity getScytheEntity() {
        return scytheEntity;
    }

    public void sync() {
        this.sync(this.livingEntity);
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

    public void setScythe(ChargoldScytheEntity scytheEntity) {
        this.scytheEntity = scytheEntity;
        this.sync();
    }
}