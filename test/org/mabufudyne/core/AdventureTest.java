package org.mabufudyne.core;

import static org.junit.Assert.*;
import org.junit.Test;

public class AdventureTest {

    @Test
    public void Constructor_ShouldCreateAnAdventureWithName_GivenTheName() {
        Adventure testAdv = new Adventure("My First Adventure");
        assertEquals("Name of the Adventure should be equal to the name that was passed to the constructor",
                "My First Adventure", testAdv.getName());
    }

    @Test
    public void Constructor_ShouldCreateAnAdventureWithDefaultName_GivenNoName() {
        Adventure testAdv = new Adventure();
        assertEquals("The Adventure name should be the default name",
                Adventure.getDefaultName(), testAdv.getName());
    }
}
