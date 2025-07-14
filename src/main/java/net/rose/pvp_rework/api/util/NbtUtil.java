package net.rose.pvp_rework.api.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class NbtUtil {
    private static boolean isInvalid(NbtCompound compound, String name) {
        return compound == null || !compound.contains(name);
    }

    public static String getStringOrDefault(NbtCompound compound, String name, String fallback) {
        if (isInvalid(compound, name)) {
            if (compound == null) return fallback;
            compound.putString(name, fallback);
        }

        return compound.getString(name);
    }

    public static boolean getBooleanOrDefault(NbtCompound compound, String name, boolean fallback) {
        if (isInvalid(compound, name)) {
            if (compound == null) return fallback;
            compound.putBoolean(name, fallback);
        }

        return compound.getBoolean(name);
    }

    public static int getIntOrDefault(NbtCompound compound, String name, int fallback) {
        if (isInvalid(compound, name)) {
            if (compound == null) return fallback;
            compound.putInt(name, fallback);
        }

        return compound.getInt(name);
    }

    public static float getFloatOrDefault(NbtCompound compound, String name, float fallback) {
        if (isInvalid(compound, name)) {
            if (compound == null) return fallback;
            compound.putFloat(name, fallback);
        }

        return compound.getFloat(name);
    }


    public static void putVec3d(NbtCompound compound, String name, Vec3d value) {
        compound.putDouble(name + "_x", value.x);
        compound.putDouble(name + "_y", value.y);
        compound.putDouble(name + "_z", value.z);
    }

    public static Vec3d getVec3d(NbtCompound compound, String name) {
        return new Vec3d(
                compound.getDouble(name + "_x"),
                compound.getDouble(name + "_y"),
                compound.getDouble(name + "_z")
        );
    }

    public static Vec3d getVec3dOrDefault(NbtCompound compound, String name, Vec3d fallback) {
        if (isInvalid(compound, name)) {
            if (compound == null) return fallback;

            if (!compound.contains(name + "_x") || !compound.contains(name + "_y") || !compound.contains(name + "_z"))
                putVec3d(compound, name, fallback);
        }

        return getVec3d(compound, name);
    }

}
