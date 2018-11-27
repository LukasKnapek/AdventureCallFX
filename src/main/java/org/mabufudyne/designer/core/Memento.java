package org.mabufudyne.designer.core;


import org.apache.commons.lang3.SerializationUtils;

import java.util.Objects;

class Memento {

    private Adventure storedAdventure;

    Memento(Adventure adv) {
        storedAdventure = (Adventure) SerializationUtils.clone(adv);
    }

    public Adventure getAdventure() {
        return storedAdventure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memento memento = (Memento) o;
        return Objects.equals(storedAdventure, memento.storedAdventure);
    }

    @Override
    public int hashCode() {

        return Objects.hash(storedAdventure);
    }
}
