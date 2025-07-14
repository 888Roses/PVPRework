package net.rose.pvp_rework.common.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import net.minecraft.entity.LivingEntity;
import net.rose.pvp_rework.api.cardinal.ExtendedEntityComponentInitializer;
import net.rose.pvp_rework.common.component.ChargoldScytheComponent;

public class ModEntityComponents extends ExtendedEntityComponentInitializer {
    public static final ComponentKey<ChargoldScytheComponent> CHARGOLD_SCYTHE = of(
            "chargold_scythe",
            ChargoldScytheComponent.class
    );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, CHARGOLD_SCYTHE, ChargoldScytheComponent::new);
    }
}
