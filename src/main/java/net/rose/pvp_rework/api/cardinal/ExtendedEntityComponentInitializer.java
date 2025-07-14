package net.rose.pvp_rework.api.cardinal;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;

import net.rose.pvp_rework.api.API;

public abstract class ExtendedEntityComponentInitializer implements EntityComponentInitializer {
    protected static <C extends Component> ComponentKey<C> of(String componentId, Class<C> componentClass) {
        return ComponentRegistry.getOrCreate(API.id(componentId), componentClass);
    }
}