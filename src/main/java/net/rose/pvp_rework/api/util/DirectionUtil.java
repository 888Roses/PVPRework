package net.rose.pvp_rework.api.util;

import net.minecraft.util.math.Direction;

public class DirectionUtil {
    public static boolean isPositive(Direction direction) {
        return direction == Direction.UP ||
                direction == Direction.EAST ||
                direction == Direction.NORTH;
    }

    public static Direction getPositive(Direction.Axis axis) {
        return switch (axis) {
            case X -> Direction.EAST;
            case Y -> Direction.UP;
            default -> Direction.NORTH;
        };
    }

    public static Direction getNegative(Direction.Axis axis) {
        return switch (axis) {
            case X -> Direction.WEST;
            case Y -> Direction.DOWN;
            default -> Direction.SOUTH;
        };
    }

    public static Direction getDirection(Direction.Axis axis, boolean isPositive) {
        return isPositive ? getPositive(axis) : getNegative(axis);
    }
}
