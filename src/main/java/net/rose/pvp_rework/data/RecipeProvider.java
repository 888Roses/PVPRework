package net.rose.pvp_rework.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.rose.pvp_rework.common.init.ModItems;

import java.util.Arrays;
import java.util.function.Consumer;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output) {
        super(output);
    }

    private Consumer<RecipeJsonProvider> consumer;

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        this.consumer = consumer;

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CHARGOLD_INGOT)
                .input(ModItems.CHARGOLD_NUGGET, 9)
                .criterion(hasItem(ModItems.CHARGOLD_NUGGET), conditionsFromItem(ModItems.CHARGOLD_NUGGET))
                .offerTo(consumer, "create_chargold_ingot");

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CHARGOLD_NUGGET, 9)
                .input(ModItems.CHARGOLD_INGOT)
                .criterion(hasItem(ModItems.CHARGOLD_INGOT), conditionsFromItem(ModItems.CHARGOLD_INGOT))
                .offerTo(consumer, "create_chargold_nuggets");

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHARGOLD_SCYTHE)
                .pattern("GG ")
                .pattern("GS ")
                .pattern(" S ")
                .input('G', ModItems.CHARGOLD_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.CHARGOLD_INGOT), conditionsFromItem(ModItems.CHARGOLD_INGOT))
                .offerTo(consumer, "create_chargold_scythe");

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHARGOLD_SICKLE)
                .pattern("GG ")
                .pattern("G G")
                .pattern(" S ")
                .input('G', ModItems.CHARGOLD_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.CHARGOLD_INGOT), conditionsFromItem(ModItems.CHARGOLD_INGOT))
                .offerTo(consumer, "create_chargold_sickle");
    }

    private void wrappedChocolateBar(ItemConvertible choco, ItemConvertible dye, ItemConvertible... ingredients) {
        var ingredientList = Arrays.stream(ingredients).toList();
        var firstIngredient = ingredientList.get(0);

        var builder = ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, choco, 1)
                .input(Items.PAPER).input(dye);
        for (var ingredient : ingredientList) builder.input(ingredient);

        builder.group("create_" + getItemPath(choco))
                .criterion(hasItem(firstIngredient), conditionsFromItem(firstIngredient))
                .offerTo(consumer, "create_" + getItemPath(choco));
    }
}
