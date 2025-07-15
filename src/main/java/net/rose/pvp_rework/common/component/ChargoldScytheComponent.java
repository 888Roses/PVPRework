package net.rose.pvp_rework.common.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.rose.pvp_rework.api.component.ExtendedComponent;
import net.rose.pvp_rework.common.entity.ChargoldScytheEntity;
import net.rose.pvp_rework.common.init.ModEntityComponents;

public class ChargoldScytheComponent implements AutoSyncedComponent,
        ExtendedComponent<ChargoldScytheComponent> {
    private final LivingEntity owner;

    public ChargoldScytheComponent(LivingEntity owner) {
        this.owner = owner;
    }

    // region Throwing

    private boolean isThrown = false;

    public boolean isThrown() {
        return this.isThrown;
    }

    public void setThrown(boolean thrown) {
        this.isThrown = thrown;
        this.sync();
    }

    // endregion

    // region Scythe Entity

    private ChargoldScytheEntity scytheEntity;

    public ChargoldScytheEntity getScytheEntity() {
        return this.scytheEntity;
    }

    public void setScythe(ChargoldScytheEntity scytheEntity) {
        this.scytheEntity = scytheEntity;
        this.sync();
    }

    // endregion

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.isThrown = nbtCompound.getBoolean("is_thrown");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putBoolean("is_thrown", this.isThrown);
    }

    public void sync() {
        this.sync(this.owner);
    }

    @Override
    public ComponentKey<ChargoldScytheComponent> getComponent() {
        return ModEntityComponents.CHARGOLD_SCYTHE;
    }
}