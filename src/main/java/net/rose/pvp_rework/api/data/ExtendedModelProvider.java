package net.rose.pvp_rework.api.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.rose.pvp_rework.api.API;
import net.rose.pvp_rework.api.block.AdaptivePillarBlock;

public abstract class ExtendedModelProvider extends FabricModelProvider {
    public ExtendedModelProvider(FabricDataOutput output) {
        super(output);
    }

    protected BlockStateModelGenerator blockGenerator;
    protected ItemModelGenerator itemGenerator;

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        this.blockGenerator = blockStateModelGenerator;
        generateBlocks();
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        this.itemGenerator = itemModelGenerator;
        generateItems();
    }

    protected abstract void generateBlocks();
    protected abstract void generateItems();


    // region Block Util

    protected void block(Block block, Item item) {
        blockGenerator.registerSimpleCubeAll(block);
        if (item != null) blockGenerator.registerParentedItemModel(item, ModelIds.getBlockModelId(block));
    }

    protected void blockWithoutModel(Block block, Item item) {
        blockGenerator.registerSimpleState(block);
        if (item != null) blockGenerator.registerParentedItemModel(item, ModelIds.getBlockModelId(block));
    }

    protected void block(Block block) {
        blockGenerator.registerSimpleCubeAll(block);
    }

    protected void blockWithoutModel(Block block) {
        blockGenerator.registerSimpleState(block);
    }

    protected void horizontalFacingBlock(Block block) {
        var model = ModelIds.getBlockModelId(block);
        blockGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block)
                        .coordinate(BlockStateVariantMap
                                .create(Properties.HORIZONTAL_FACING)
                                .register(
                                        Direction.NORTH,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, model)
                                )
                                .register(
                                        Direction.EAST,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, model)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .register(
                                        Direction.SOUTH,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, model)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R180)
                                )
                                .register(
                                        Direction.WEST,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, model)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        )
        );
    }

    protected void columnBlockWithItem(Block block, Item item) {
        var info = columnBlock(block);
        blockGenerator.registerParentedItemModel(item, info.middleTopBottom());
    }

    protected ColumnBlockModels columnBlock(Block block) {
        var columnMiddleTopBottomId = Models.CUBE_COLUMN.upload(
                block,
                "_middle_top_bottom",
                TextureMap.sideEnd(
                        TextureMap.getSubId(block, "_middle_top_bottom"),
                        TextureMap.getSubId(block, "_up")
                ),
                blockGenerator.modelCollector
        );
        var columnMiddleId = Models.CUBE_COLUMN.upload(
                block,
                "_middle",
                TextureMap.sideEnd(
                        TextureMap.getSubId(block, "_middle"),
                        TextureMap.getSubId(block, "_up")
                ),
                blockGenerator.modelCollector
        );
        var columnUpId = Models.CUBE_COLUMN.upload(
                block,
                "_middle_top",
                TextureMap.sideEnd(
                        TextureMap.getSubId(block, "_middle_top"),
                        TextureMap.getSubId(block, "_up")
                ),
                blockGenerator.modelCollector
        );
        var columnDownId = Models.CUBE_COLUMN.upload(
                block,
                "_middle_bottom",
                TextureMap.sideEnd(
                        TextureMap.getSubId(block, "_middle_bottom"),
                        TextureMap.getSubId(block, "_up")
                ),
                blockGenerator.modelCollector
        );
        blockGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block)
                        .coordinate(BlockStateVariantMap
                                .create(Properties.AXIS, AdaptivePillarBlock.UP, AdaptivePillarBlock.DOWN)
                                .register(
                                        Direction.Axis.X, false, false,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnMiddleTopBottomId)
                                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .register(
                                        Direction.Axis.Y, false, false,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnMiddleTopBottomId)
                                )
                                .register(
                                        Direction.Axis.Z, false, false,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnMiddleTopBottomId)
                                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                )

                                .register(
                                        Direction.Axis.X, false, true,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnUpId)
                                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .register(
                                        Direction.Axis.Y, false, true,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnUpId)
                                )
                                .register(
                                        Direction.Axis.Z, false, true,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnUpId)
                                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                )

                                .register(
                                        Direction.Axis.X, true, false,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnDownId)
                                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .register(
                                        Direction.Axis.Y, true, false,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnDownId)
                                )
                                .register(
                                        Direction.Axis.Z, true, false,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnDownId)
                                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                )

                                .register(
                                        Direction.Axis.X, true, true,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnMiddleId)
                                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .register(
                                        Direction.Axis.Y, true, true,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnMiddleId)
                                )
                                .register(
                                        Direction.Axis.Z, true, true,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, columnMiddleId)
                                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                )
                        )
        );

        return new ColumnBlockModels(columnMiddleTopBottomId, columnMiddleId, columnUpId, columnDownId);
    }

    protected void slab(Block block, Block template, Item item) {
        final var templateId = TextureMap.getId(template);
        final TextureMap templateTexture = TextureMap.all(templateId);
        final var slabBottomModelId = Models.SLAB.upload(block, templateTexture, blockGenerator.modelCollector);
        final var slabTopModelId = Models.SLAB_TOP.upload(block, templateTexture, blockGenerator.modelCollector);
        blockGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(
                block,
                slabBottomModelId, slabTopModelId, templateId
        ));
        blockGenerator.registerParentedItemModel(item, slabBottomModelId);
    }

    protected void stairs(Block block, Block template, Item item) {
        final TextureMap templateTexture = TextureMap.all(TextureMap.getId(template));
        final var stairsModelId = Models.STAIRS.upload(block, templateTexture, blockGenerator.modelCollector);
        final var innerStairsModelId = Models.INNER_STAIRS.upload(block, templateTexture,
                blockGenerator.modelCollector);
        final var outerStairsModelId = Models.OUTER_STAIRS.upload(block, templateTexture,
                blockGenerator.modelCollector);
        blockGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(
                block,
                innerStairsModelId, stairsModelId, outerStairsModelId
        ));
        blockGenerator.registerParentedItemModel(item, stairsModelId);
    }

    // endregion

    // region Item Util

    protected void item(Item item) {
        itemGenerator.register(item, Models.GENERATED);
    }

    protected void layeredItem(Item item, String idA, String idB) {
        Models.GENERATED_TWO_LAYERS.upload(
                ModelIds.getItemModelId(item),
                TextureMap.layered(
                        API.id(idA).withPrefixedPath("item/"),
                        API.id(idB).withPrefixedPath("item/")
                ),
                itemGenerator.writer
        );
    }

    protected void layeredItem(Item item, String idA, String idB, String idC) {
        Models.GENERATED_THREE_LAYERS.upload(
                ModelIds.getItemModelId(item),
                TextureMap.layered(
                        API.id(idA).withPrefixedPath("item/"),
                        API.id(idB).withPrefixedPath("item/"),
                        API.id(idC).withPrefixedPath("item/")
                ),
                itemGenerator.writer
        );
    }

    // endregion

    public record ColumnBlockModels(Identifier middleTopBottom, Identifier middle,
                                    Identifier top, Identifier bottom) {}
}