package net.rose.pvp_rework.common.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.rose.pvp_rework.api.component.ExtendedComponent;
import net.rose.pvp_rework.common.init.ModEntityComponents;
import net.rose.pvp_rework.common.init.ModItems;
import org.jetbrains.annotations.NotNull;

public class ChargoldSickleComponent implements ExtendedComponent<ChargoldSickleComponent>,
        AutoSyncedComponent,
        CommonTickingComponent {

    private final PlayerEntity player;
    private double equipAnimationProgress;
    private double previousEquipAnimationProgress;
    private int previousSelectedSlot;

    public ChargoldSickleComponent(PlayerEntity player) {
        this.player = player;
    }

    private void tickCurrentSlot() {
        final var inventory = player.getInventory();
        final var currentSelectedSlot = inventory.selectedSlot;

        if (currentSelectedSlot != this.previousSelectedSlot && inventory.getStack(currentSelectedSlot).isOf(ModItems.CHARGOLD_SICKLE)) {
            this.equipAnimationProgress = 0.0;
            this.previousEquipAnimationProgress = 0.0;
        }

        this.previousSelectedSlot = currentSelectedSlot;
    }

    public double getCurrentAnimationProgress(double tickDelta) {
        return MathHelper.lerp(tickDelta, this.previousEquipAnimationProgress, this.equipAnimationProgress);
    }

    private void tickEquipAnimation() {
        this.player.sendMessage(Text.literal("Progress: " + this.equipAnimationProgress), true);

        if (this.equipAnimationProgress == 1) {
            this.previousEquipAnimationProgress = this.equipAnimationProgress;
            return;
        }

        this.previousEquipAnimationProgress = this.equipAnimationProgress;
        this.equipAnimationProgress = MathHelper.clamp(this.equipAnimationProgress + 0.075f * 2, 0f, 1f);
    }

    @Override
    public void clientTick() {
        CommonTickingComponent.super.clientTick();

        tickCurrentSlot();
        tickEquipAnimation();
    }

    @Override
    public void tick() {
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound nbtCompound) {}

    @Override
    public void writeToNbt(@NotNull NbtCompound nbtCompound) {}

    @Override
    public ComponentKey<ChargoldSickleComponent> getComponent() {
        return ModEntityComponents.CHARGOLD_SICKLE;
    }
}
