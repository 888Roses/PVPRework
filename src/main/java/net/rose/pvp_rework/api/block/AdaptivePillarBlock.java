package net.rose.pvp_rework.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.rose.pvp_rework.api.util.DirectionUtil;
import org.jetbrains.annotations.Nullable;

public class AdaptivePillarBlock extends Block {
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public AdaptivePillarBlock(Settings settings) {
        super(settings);

        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(UP, false).with(DOWN, false).with(AXIS, Direction.Axis.Y)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(UP, DOWN, AXIS);
    }

    protected boolean isPillar(World world, BlockPos pos, Direction direction, boolean isOpposite) {
        return world.getBlockState(isOpposite ? pos.subtract(direction.getVector()) : pos.add(direction.getVector())).isOf(this);
    }

    // Problem: When placing a pillar above another pillar both UP and DOWN are true.
    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getPillarWithProperties(
                super.getPlacementState(ctx).with(AXIS, ctx.getSide().getAxis()),
                ctx.getBlockPos(), ctx.getWorld()
        );
    }

    protected BlockState getPillarWithProperties(BlockState state, BlockPos pos, World world) {
        var axis = state.get(AXIS);
        var upState = world.getBlockState(pos.offset(DirectionUtil.getPositive(axis)));
        var downState = world.getBlockState(pos.offset(DirectionUtil.getNegative(axis)));

        var up = upState.isOf(this) && upState.get(AXIS) == axis;
        var down = downState.isOf(this) && downState.get(AXIS) == axis;
        return state.with(UP, up).with(DOWN, down);
    }

    protected BlockState getPillarWithProperties(BlockState state, WorldAccess world, BlockPos pos) {
        var axis = state.get(AXIS);
        var upState = world.getBlockState(pos.offset(DirectionUtil.getPositive(axis)));
        var downState = world.getBlockState(pos.offset(DirectionUtil.getNegative(axis)));

        var up = upState.isOf(this) && upState.get(AXIS) == axis;
        var down = downState.isOf(this) && downState.get(AXIS) == axis;
        return state.with(UP, up).with(DOWN, down);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return getPillarWithProperties(
                super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos),
                world, pos
        );
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return changeRotation(state, rotation);
    }

    public static BlockState changeRotation(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.get(AXIS)) {
                case X -> state.with(AXIS, Direction.Axis.Z);
                case Z -> state.with(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }
}
