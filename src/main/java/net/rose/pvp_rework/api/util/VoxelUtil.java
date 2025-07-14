package net.rose.pvp_rework.api.util;

import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class VoxelUtil {
    /**
     * Combines every given {@link VoxelShape} into one singular {@link VoxelShape}.
     *
     * @param shapes All of the {@link VoxelShape} that when combined form the output shape.
     * @return A new {@link VoxelShape} made out of the combined {@link VoxelShape} shapes using the
     * {@link BooleanBiFunction#OR} bi function.
     */
    public static final VoxelShape all(VoxelShape... shapes) {
        VoxelShape result = VoxelShapes.empty();
        for (var shape : shapes) result = VoxelShapes.combine(result, shape, BooleanBiFunction.OR);
        return result;
    }

    /**
     * Builds a {@link VoxelShape} using a box layout (position and size) rather than a Bounds layout (min-max).
     *
     * @param x     The position X of the box. Ranges between 0 and 16, where 16 is the size of a block.
     * @param y     The position Y of the box. Ranges between 0 and 16, where 16 is the size of a block.
     * @param z     The position Z of the box. Ranges between 0 and 16, where 16 is the size of a block.
     * @param sizeX The size X of the box. Ranges between 0 and 16, where 16 is the size of a block.
     * @param sizeY The size Y of the box. Ranges between 0 and 16, where 16 is the size of a block.
     * @param sizeZ The size Z of the box. Ranges between 0 and 16, where 16 is the size of a block.
     * @return A new {@link VoxelShape} representing the described box.
     * @apiNote Unlike {@link VoxelShapes#cuboid(double, double, double, double, double, double)}, the values range
     * between 0 and 16. <p/> When building the {@link VoxelShape} representing the described box, the size X Y and Z
     * are added to the pos X Y and Z to determine the extent of the box.
     */
    public static VoxelShape cube(double x, double y, double z, double sizeX, double sizeY, double sizeZ) {
        return VoxelShapes.cuboid(
                x / 16d, y / 16d, z / 16d,
                (x + sizeX) / 16d, (y + sizeY) / 16d, (z + sizeZ) / 16d
        );
    }
}
