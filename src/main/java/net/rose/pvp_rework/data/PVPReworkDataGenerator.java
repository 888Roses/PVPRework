package net.rose.pvp_rework.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PVPReworkDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final var pack = fabricDataGenerator.createPack();
        pack.addProvider(LangProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(ModelProvider::new);
    }
}
