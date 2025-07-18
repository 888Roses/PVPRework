package net.rose.pvp_rework.common.init;

import net.minecraft.sound.SoundEvent;
import net.rose.pvp_rework.common.PVPRework;

public class ModSounds {
    public static final SoundEvent CHARGOLD_SCYTHE_HIT = of("item.chargold_scythe.hit");
    public static final SoundEvent CHARGOLD_SCYTHE_THROW = of("item.chargold_scythe.throw");
    public static final SoundEvent CHARGOLD_SCYTHE_TRAVEL = of("item.chargold_scythe.travel");
    public static final SoundEvent CHARGOLD_SCYTHE_BOUNCE = of("item.chargold_scythe.bounce");
    public static final SoundEvent CHARGOLD_SCYTHE_RETURN = of("item.chargold_scythe.return");

    public static final SoundEvent CHARGOLD_SICKLE_EQUIP = of("item.chargold_sickle.equip");
    public static final SoundEvent CHARGOLD_SICKLE_ATTACK = of("item.chargold_sickle.attack");
    public static final SoundEvent CHARGOLD_SICKLE_SWAP = of("item.chargold_sickle.swap");

    public static final SoundEvent CHARGOLD_HUMBLER_SPIKE = of("item.chargold_humbler.spike");

    // region BackEnd

    private static SoundEvent of(String path) {
        return SoundEvent.of(PVPRework.id(path));
    }

    public static void init() {
        PVPRework.LOGGER.info("[{}]: Initialized Mod Sounds!", PVPRework.MOD_ID);
    }

    // endregion
}
