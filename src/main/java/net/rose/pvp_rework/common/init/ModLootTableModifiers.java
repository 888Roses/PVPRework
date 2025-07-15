package net.rose.pvp_rework.common.init;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.rose.pvp_rework.common.PVPRework;

public class ModLootTableModifiers {
    public static void init() {
        PVPRework.LOGGER.info("[{}]: Initialized Mod Loot Table Modifiers!", PVPRework.MOD_ID);

        LootTableEvents.MODIFY.register((
                resourceManager,
                lootManager,
                identifier,
                builder,
                lootTableSource
        ) -> {
            if (identifier.getPath().contains("husk")) {
                builder.pool(new LootPool.Builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1F/20F))
                        .with(ItemEntry.builder(ModItems.CHARGOLD_NUGGET))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)).build())
                        .build());

                builder.pool(new LootPool.Builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1F/1000F))
                        .with(ItemEntry.builder(ModItems.CHARGOLD_INGOT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)).build())
                        .build());
            }
        });
    }
}
