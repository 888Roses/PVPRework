package net.rose.pvp_rework.common.init;

import net.minecraft.sound.SoundEvent;
import net.rose.pvp_rework.common.PVPRework;

public class ModSounds {
    public static final SoundEvent CHARGOLD_SCYTHE_HIT = of("item.chargold_scythe.hit");
    public static final SoundEvent CHARGOLD_SCYTHE_THROW = of("item.chargold_scythe.throw");
    public static final SoundEvent CHARGOLD_SCYTHE_TRAVEL = of("item.chargold_scythe.travel");
    public static final SoundEvent CHARGOLD_SCYTHE_BOUNCE = of("item.chargold_scythe.bounce");

    // region BackEnd

    private static SoundEvent of(String path) {
        return SoundEvent.of(PVPRework.id(path));
    }

    public static void init() {
        PVPRework.LOGGER.info("[{}]: Initialized Mod Sounds!", PVPRework.MOD_ID);
    }

    // endregion
}
