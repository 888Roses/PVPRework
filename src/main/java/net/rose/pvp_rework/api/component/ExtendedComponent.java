package net.rose.pvp_rework.api.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;

public interface ExtendedComponent<T extends Component> {
    ComponentKey<T> getComponent();

    default void sync(Object target) {
        getComponent().sync(target);
    }
}
