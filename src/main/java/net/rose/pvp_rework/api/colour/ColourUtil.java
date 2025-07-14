package net.rose.pvp_rework.api.colour;

import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ColourUtil {
    public static Vector3d colourToRgb(Color colour) {
        return new Vector3d(colour.getRed(), colour.getGreen(), colour.getBlue());
    }

    public static Color rgbToColour(Vector3d rgb) {
        return new Color(MathHelper.clamp((int) rgb.x, 0, 255), MathHelper.clamp((int) rgb.y, 0, 255),
                MathHelper.clamp((int) rgb.z, 0, 255));
    }

    public static Vector3d hexToRgb(int hex) {
        return colourToRgb(new Color(hex));
    }

    public static int rgbToHex(Vector3d rgb) {
        return rgbToColour(rgb).getRGB();
    }

    public static Vector3d rgbToRyb(Vector3d rgb) {
        var iw = Math.min(Math.min(rgb.x, rgb.y), rgb.z);
        var ib = Math.min(Math.min(255 - rgb.x, 255 - rgb.y), 255 - rgb.z);
        var rRGB = rgb.x - iw;
        var gRGB = rgb.y - iw;
        var bRGB = rgb.z - iw;
        var minRG = Math.min(rRGB, gRGB);
        var rRYB = rRGB - minRG;
        var yRYB = (gRGB + minRG) / 2;
        var bRYB = (bRGB + gRGB - minRG) / 2;
        var n = Math.max(Math.max(Math.max(rRYB, yRYB), bRYB), 1) / Math.max(Math.max(Math.max(rRGB, gRGB), bRGB), 1);
        return new Vector3d(rRYB / n + ib, yRYB / n + ib, bRYB / n + ib);
    }

    public static Vector3d rybToRgb(Vector3d ryb) {
        var iw = Math.min(Math.min(ryb.x, ryb.y), ryb.z);
        var ib = Math.min(Math.min(255 - ryb.x, 255 - ryb.y), 255 - ryb.z);
        var rRYB = ryb.x - iw;
        var yRYB = ryb.y - iw;
        var bRYB = ryb.z - iw;
        var minYB = Math.min(yRYB, bRYB);
        var rRGB = rRYB + yRYB - minYB;
        var gRGB = yRYB + minYB;
        var bRGB = 2 * (bRYB - minYB);
        var n = Math.max(Math.max(Math.max(rRGB, gRGB), bRGB), 1) / Math.max(Math.max(Math.max(rRYB, yRYB), bRYB), 1);
        return new Vector3d(rRGB / n + ib, gRGB / n + ib, bRGB / n + ib);
    }

    public static Vector3d mixRyb(Vector3d ryb1, Vector3d ryb2) {
        return new Vector3d(
                Math.min(255, ryb1.x + ryb2.x),
                Math.min(255, ryb1.y + ryb2.y),
                Math.min(255, ryb1.z + ryb2.z)
        );
    }

    public static Vector3d mixRgb(Vector3d rgb1, Vector3d rgb2) {
        return rybToRgb(mixRyb(rgbToRyb(rgb1), rgbToRyb(rgb2)));
    }

    public static @Nullable Vector3d mixRybs(List<Vector3d> rybs) {
        var current = rybs.get(0);
        if (current == null) return null;
        for (var i = 1; i < rybs.size(); i++) current = mixRyb(current, rybs.get(i));
        return current;
    }

    public static Vector3d mixRgbs(List<Vector3d> rgbs) {
        return rybToRgb(Objects.requireNonNull(mixRybs(rgbs.stream().map(ColourUtil::rgbToRyb).toList())));
    }

    public static Vector3d mixColours(List<Color> colours) {
        return rybToRgb(Objects.requireNonNull(mixRybs(colours.stream().map(ColourUtil::colourToRgb).map(ColourUtil::rgbToRyb).toList())));
    }
}
