package org.mabufudyne.designer.core;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MementoTest {

    private Adventure defaultAdventure;

    @Before
    public void createDefaultObjects() {
        defaultAdventure = new Adventure();
    }

    @Test
    public void Constructor_ShouldCreateAMementoWithTheGivenAdventure_GivenTheAdventure() {
        Memento newMemento = new Memento(defaultAdventure);
        assertEquals(defaultAdventure, newMemento.getAdventure(),
                "Memento does not contain the Adventure that was given to it.");
    }

    @Test
    public void EqualsHashCode_ShouldBeReflexive() {
        Memento state = new Memento(defaultAdventure);

        assertEquals(state, state,
                "A Memento is not considered equal to itself.");
        assertEquals(state.hashCode(), state.hashCode(),
                "A Memento hashcode is not reflexive.");
    }

    @Test
    public void EqualsHashCode_ShouldBeSymmetric() {
        Memento state1 = new Memento(defaultAdventure);
        Memento state2 = new Memento(defaultAdventure);

        assertEquals(state1, state2,
                "Two Mementos with the same Adventure are not considered equal.");
        assertEquals(state1.hashCode(), state2.hashCode(),
                "Two Mementos with the same Adventure have different hash codes.");
    }

    @Test
    public void EqualsHashCode_ShouldBeTransitive() {
        Memento state1 = new Memento(defaultAdventure);
        Memento state2 = new Memento(defaultAdventure);
        Memento state3 = new Memento(defaultAdventure);

        assertTrue(state1.equals(state2) &&
                        state2.equals(state3) &&
                        state1.equals(state3),
                "The transitive property does not apply to three equal Mementos.");
        assertTrue(state1.hashCode() == state2.hashCode() &&
                        state2.hashCode() == state3.hashCode() &&
                        state1.hashCode() == state3.hashCode(),
                "The transitive property doest not apply to hash codes of three equal Mementos.");
    }

}
