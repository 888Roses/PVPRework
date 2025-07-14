package net.rose.pvp_rework.api.util;

import org.joml.Quaternionf;

public class MatrixUtil {
    public static Quaternionf fromEulerAngles(double x, double y, double z) {
        return fromEulerAnglesRad((float) Math.toRadians(x), (float) Math.toRadians(y), (float) Math.toRadians(z));
    }

    public static Quaternionf fromEulerAnglesRad(float x, float y, float z) {
        return new Quaternionf().rotationXYZ(x, y, z);
    }
}
