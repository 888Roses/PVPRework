package net.rose.pvp_rework.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.entity.ChargoldScytheEntity;

public class ModEntityTypes {
    public static final EntityType<ChargoldScytheEntity> CHARGOLD_SCYTHE = of(
            "chargold_scythe",
            EntityType.Builder.create(ChargoldScytheEntity::new, SpawnGroup.MISC)
                    .setDimensions(2F, 0.5F)
    );

    public static <T extends Entity> EntityType<T> of(String path, EntityType.Builder<T> entity) {
        return Registry.register(
                Registries.ENTITY_TYPE, PVPRework.id(path),
                entity.build(path)
        );
    }

    public static void init() {
        PVPRework.LOGGER.info("[{}]: Initialized Mod Entity Types!", PVPRework.MOD_ID);
    }
}
