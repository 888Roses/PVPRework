package net.rose.pvp_rework.api.stance;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.At;

public abstract class CustomStance {
    /**
     * Whether the {@link LivingEntity}'s {@link BipedEntityModel} should be using this biped stance or not.
     *
     * @param livingEntity the {@link LivingEntity} that may use this custom stance.
     * @return True if the {@link LivingEntity} should be using that stance, false otherwise.
     * @apiNote This method is injected {@link At} the <u>"HEAD"</u> of
     * {@link BipedEntityModel#positionRightArm(LivingEntity)} and
     * {@link BipedEntityModel#positionLeftArm(LivingEntity)}, meaning that this check will overshadow any existing
     * vanilla check.
     */
    public abstract boolean matches(LivingEntity livingEntity);

    /**
     * Applies a transformation on a given {@link BipedEntityModel} model. Provides the a {@link LivingEntity} of type T
     * and a {@link StanceType} describing when in the render player method is this method called.
     *
     * @param model        The model of the entity matching this stance.
     * @param livingEntity The entity affected by this stance.
     * @param stanceType     When this method is executed in the model.
     * @param <T>          The type of {@link LivingEntity} that this stance is applied on.
     * @apiNote This method is called multiple times; One for each value of {@link StanceType}.
     */
    public abstract <T extends LivingEntity> void apply(BipedEntityModel<T> model, T livingEntity, StanceType stanceType);

    public enum StanceType {
        SET_RIGHT_ARM, SET_LEFT_ARM,
        SET_BODY_TAIL, SET_BODY_HEAD
    }
}
