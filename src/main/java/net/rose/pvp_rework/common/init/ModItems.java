package net.rose.pvp_rework.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.item.ChargoldScytheItem;

public class ModItems {
    public static final Item CHARGOLD_SCYTHE = of("chargold_scythe", new ChargoldScytheItem());

    // region BackEnd

    private static Item of(String name, Item item) {
        return Registry.register(
                Registries.ITEM,
                PVPRework.id(name),
                item
        );
    }

    private static Item of(String name, Item.Settings settings) {
        return of(name, new Item(settings));
    }

    private static Item of(String name) {
        return of(name, new Item.Settings());
    }

    private static Item createBlockItem(String path, Block block) {
        return of(path, new BlockItem(block, new Item.Settings()));
    }

    public static void init() {
        PVPRework.LOGGER.info("[{}]: Initialized Mod Items!", PVPRework.MOD_ID);
    }

    // endregion
}
