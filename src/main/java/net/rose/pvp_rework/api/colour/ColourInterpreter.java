package net.rose.pvp_rework.api.colour;

import java.awt.*;

public class ColourInterpreter {
    public static String getColourName(int colour) {
        var rgb = new Color(colour);
        var bestMatch = "Undefined";
        var bestMatchDistance = Double.POSITIVE_INFINITY;

        for (var c : ColourRegistry.COLOUR_NAMES) {
            var d = Math.pow(c.r() - rgb.getRed(), 2) +
                    Math.pow(c.g() - rgb.getGreen(), 2) +
                    Math.pow(c.b() - rgb.getBlue(), 2);

            if (d < bestMatchDistance) {
                bestMatchDistance = d;
                bestMatch = c.name();
            }
        }

        return bestMatch;
    }
}
