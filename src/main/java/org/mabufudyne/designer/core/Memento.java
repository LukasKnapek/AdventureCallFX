package org.mabufudyne.designer.core;


import org.apache.commons.lang3.SerializationUtils;

import java.util.Objects;

public class Memento {

    private Adventure storedAdventure;

    public Memento(Adventure adv) {
        Adventure deepCopyAdv = (Adventure) SerializationUtils.clone(adv);
        storedAdventure = deepCopyAdv;
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
