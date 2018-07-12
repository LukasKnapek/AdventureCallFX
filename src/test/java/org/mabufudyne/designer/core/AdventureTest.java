package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mabufudyne.designer.core.Adventure;
import org.mabufudyne.designer.core.StoryPiece;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AdventureTest {

    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    @BeforeEach
    protected void createDefaultObjects() {
        defaultAdventure = new Adventure();
        defaultStoryPiece = new StoryPiece();
    }

    @Test
    public void Constructor_ShouldCreateAnAdventureWithName_GivenTheName() {
        Adventure testAdv = new Adventure("My First Adventure");
        assertEquals("My First Adventure", testAdv.getName(),
                "Name of the Adventure should be equal to the name that was passed to the constructor");
    }

    @Test
    public void Constructor_ShouldCreateAnAdventureWithDefaultName_GivenNoName() {
        assertEquals(Adventure.getDefaultName(), defaultAdventure.getName(),
                "The Adventure name should be the default name");
    }

    @Test
    public void Constructor_ShouldCreateAnAdventureWithOneStoryPiece() {
        assertEquals(1, defaultAdventure.getStoryPieces().size(),
                "The Adventure has to contain at least one StoryPiece at all times," +
                        " including immediately after creation");
    }

    @Test
    public void AddStoryPiece_ShouldAddStoryPieceToTheAdventure_GivenAStoryPiece() {
        defaultAdventure.addStoryPiece(defaultStoryPiece);
        assertTrue(defaultAdventure.getStoryPieces().contains(defaultStoryPiece),
                "The Adventure does not contain the StoryPiece that was added to it");
    }

    @Test
    public void AddStoryPiece_ShouldAddStoryPiecesToTheAdventure_GivenMultipleStoryPieces() {
        StoryPiece[] storyPieces = new StoryPiece[5];
        for (int i=0; i<5; i++) {
            storyPieces[i] = new StoryPiece();
            defaultAdventure.addStoryPiece(storyPieces[i]);
        }

        for (StoryPiece sp : storyPieces) {
            assertTrue(defaultAdventure.getStoryPieces().contains(sp),
                    "The Adventure does not contain one of the StoryPieces that were added to it");
        }
    }

    @Test
    public void AddStoryPiece_ShouldNotAddStoryPieceToTheAdventure_GivenTheAdventureAlreadyContainsTheStoryPiece() {
        defaultAdventure.addStoryPiece(defaultStoryPiece);
        defaultAdventure.addStoryPiece(defaultStoryPiece);

        assertTrue(Collections.frequency(defaultAdventure.getStoryPieces(), defaultStoryPiece) == 1,
                "The Adventure should not add the StoryPiece if it already exists inside the Adventure");
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPiece_GivenTheStoryPieces() {
        defaultAdventure.addStoryPiece(defaultStoryPiece);
        defaultAdventure.removeStoryPiece(defaultStoryPiece);

        assertFalse(defaultAdventure.getStoryPieces().contains(defaultStoryPiece),
                "The Adventure should not contain the StoryPiece that was removed from it");
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPieces_GivenTheStoryPieces() {
        StoryPiece[] storyPieces = new StoryPiece[5];
        for (int i=0; i<5; i++) {
            storyPieces[i] = new StoryPiece();
            defaultAdventure.addStoryPiece(storyPieces[i]);
        }

        for (StoryPiece sp : storyPieces) {
            defaultAdventure.removeStoryPiece(sp);
            assertFalse(defaultAdventure.getStoryPieces().contains(sp),
                    "The Adventure should not contain one of the StoryPieces that were removed from it");
        }
    }

    @Test
    public void RemoveStoryPiece_ShouldNotRemovyStoryPiece_GivenItIsTheLastOne() {
        StoryPiece sp = defaultAdventure.getStoryPieces().get(0);
        defaultAdventure.removeStoryPiece(sp);
        assertTrue(defaultAdventure.getStoryPieces().contains(sp),
                "The Adventure should not have removed the last StoryPiece it has");
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPiecesExceptForTheLastOne_GivenTheStoryPieces() {
        StoryPiece[] storyPieces = new StoryPiece[5];
        storyPieces[0] = defaultAdventure.getStoryPieces().get(0);

        for (int i=1; i<5; i++) {
            storyPieces[i] = new StoryPiece();
            defaultAdventure.addStoryPiece(storyPieces[i]);
        }

        for (StoryPiece sp : storyPieces) {
            defaultAdventure.removeStoryPiece(sp);
            assertTrue(defaultAdventure.getStoryPieces().size() > 0,
                    "The Adventure should not have removed the last StoryPiece");
        }
    }

    @Test
    public void RemoveStoryPiece_ShouldThrowExceptionWhenTryingToRemoveStoryPiece_GivenTheAdventureDoesNotContainTheStoryPiece() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            defaultAdventure.removeStoryPiece(defaultStoryPiece);
        });
        assertEquals("Adventure does not contain the given StoryPiece.", ex.getMessage(),
                "The exception did not have the correct message.");
    }

    @Test
    public void RemoveStoryPiece_ShouldOnlyRemoveSpecificStoryPieces_GivenTheStoryPieces() {
        // Add 10 choices to a StoryPiece, remove random number of them, check that the rest still remains
        ArrayList<StoryPiece> spList = new ArrayList<>();

        for (int i=0; i<10; i++) {
            StoryPiece sp = new StoryPiece();
            spList.add(sp);
            defaultAdventure.addStoryPiece(sp);
        }

        Random rand = new Random();
        int numOfChosen = rand.nextInt(10);

        for (int i=0; i<numOfChosen; i++) {
            int randIndex = rand.nextInt(spList.size()-1);
            StoryPiece removedSP = spList.remove(randIndex);
            defaultAdventure.removeStoryPiece(removedSP);
        }

        for (StoryPiece remainingSP : spList) {
            assertTrue(defaultAdventure.getStoryPieces().contains(remainingSP),
                    "Adventure no longer contains a StoryPiece that was not supposed to be removed.");
        }
    }

}
