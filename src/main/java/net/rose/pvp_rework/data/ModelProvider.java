package net.rose.pvp_rework.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.rose.pvp_rework.api.data.ExtendedModelProvider;
import net.rose.pvp_rework.common.init.ModItems;

public class ModelProvider extends ExtendedModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected void generateBlocks() {
    }

    @Override
    protected void generateItems() {
        item(ModItems.CHARGOLD_SCYTHE);
        item(ModItems.CHARGOLD_SICKLE);
        item(ModItems.CHARGOLD_HUMBLER);

        item(ModItems.CHARGOLD_INGOT);
        item(ModItems.CHARGOLD_NUGGET);
    }
}
