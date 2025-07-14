package net.rose.pvp_rework.api.util;

import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ColourUtil {
    public static String getColourName(int rgb) {
        if (rgb == 0xFf005f) return "Folly Red";

        var colour = new Color(rgb);
        var hsb = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), null);
        var hueName = getHueName(hsb[0]);
        var saturationName = getSaturationName(hsb[1]);
        var brightnessName = getBrightnessName(hsb[2]);

        var builder = new StringBuilder();
        if (!brightnessName.isEmpty()) builder.append(brightnessName).append(" ");
        if (!saturationName.isEmpty()) builder.append(saturationName).append(" ");
        builder.append(hueName);

        return builder.toString();
    }

    private static String getSaturationName(float saturation) {
        return SATURATION_NAMES[MathHelper.clamp((int) (saturation * SATURATION_NAMES.length), 0,
                SATURATION_NAMES.length - 1)];
    }

    private static String getBrightnessName(float brightness) {
        return BRIGHTNESS_NAMES[MathHelper.clamp((int) (brightness * BRIGHTNESS_NAMES.length), 0,
                BRIGHTNESS_NAMES.length - 1)];
    }

    private static String getHueName(float hue) {
        return HUE_NAMES[MathHelper.clamp((int) (hue * HUE_NAMES.length), 0, HUE_NAMES.length - 1)];
    }

    private static final String[] SATURATION_NAMES = {
            "Very Desaturated",
            "Desaturated",
            "",
            "Saturated",
            "Very Saturated"
    };

    private static final String[] BRIGHTNESS_NAMES = {
            "Very Dark",
            "Dark",
            "",
            "Light",
            "Bright",
            "Very Bright"
    };

    private static final String[] HUE_NAMES = {
            "Red",
            "Orange",
            "Yellow",
            "Lime",
            "Green",
            "Turquoise",
            "Cyan",
            "Light Blue",
            "Blue",
            "Night",
            "Purple",
            "Magenta",
            "Pink",
            "Red"
    };
}
