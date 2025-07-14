package net.rose.pvp_rework.api.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Vec3Util {
    public static Vec3d lerp(float delta, Vec3d a, Vec3d b) {
        return lerp(delta, a, b);
    }

    public static Vec3d lerp(double delta, Vec3d a, Vec3d b) {
        return new Vec3d(
                MathHelper.lerp(delta, a.x, b.x),
                MathHelper.lerp(delta, a.y, b.y),
                MathHelper.lerp(delta, a.z, b.z)
        );
    }
}
