package net.rose.pvp_rework.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.rose.pvp_rework.common.PVPRework;

public class ModItemGroups {
    public static final ItemGroup MAIN = Registry.register(
            Registries.ITEM_GROUP, PVPRework.id(PVPRework.MOD_ID),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup." + PVPRework.MOD_ID))
                    .icon(() -> new ItemStack(ModItems.CHARGOLD_SCYTHE))
                    .entries(ModItemGroups::populateMain)
                    .build()
    );

    private static void populateMain(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        entries.add(ModItems.CHARGOLD_INGOT);
        entries.add(ModItems.CHARGOLD_NUGGET);
        entries.add(ModItems.CHARGOLD_SCYTHE);
        entries.add(ModItems.CHARGOLD_SICKLE);
        entries.add(ModItems.CHARGOLD_HUMBLER);
    }

    public static void init() {
        PVPRework.LOGGER.info("[{}]: Initialized Mod Item Groups!", PVPRework.MOD_ID);
    }
}
