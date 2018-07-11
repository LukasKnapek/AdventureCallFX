package org.mabufudyne.core;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Collections;

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

    @Test
    public void Constructor_ShouldCreateAnAdventureWithOneStoryPiece() {
        Adventure testAdv = new Adventure();
        assertEquals("The Adventure has to contain at least one StoryPiece at all times," +
                        " including immediately after creation",
                1, testAdv.getStoryPieces().size());
    }

    @Test
    public void AddStoryPiece_ShouldAddStoryPieceToTheAdventure_GivenAStoryPiece() {
        Adventure testAdv = new Adventure();
        StoryPiece sp = new StoryPiece();
        testAdv.addStoryPiece(sp);
        assertTrue("The Adventure does not contain the StoryPiece that was added to it",
                testAdv.getStoryPieces().contains(sp));
    }

    @Test
    public void AddStoryPiece_ShouldAddStoryPiecesToTheAdventure_GivenMultipleStoryPieces() {
        Adventure testAdv = new Adventure();
        StoryPiece[] storyPieces = new StoryPiece[5];
        for (int i=0; i<5; i++) {
            storyPieces[i] = new StoryPiece();
            testAdv.addStoryPiece(storyPieces[i]);
        }

        for (StoryPiece sp : storyPieces) {
            assertTrue("The Adventure does not contain one of the StoryPieces that were added to it",
                    testAdv.getStoryPieces().contains(sp));
        }
    }

    @Test
    public void AddStoryPiece_ShouldNotAddStoryPieceToTheAdventure_GivenTheAdventureAlreadyContainsTheStoryPiece() {
        Adventure testAdv = new Adventure();
        StoryPiece sp = new StoryPiece();

        testAdv.addStoryPiece(sp);
        testAdv.addStoryPiece(sp);

        assertTrue("The Adventure should not add the StoryPiece if it already exists inside the Adventure",
                Collections.frequency(testAdv.getStoryPieces(), sp) == 1);
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPiece_GivenTheStoryPieces() {
        Adventure testAdv = new Adventure();
        StoryPiece sp = new StoryPiece();

        testAdv.addStoryPiece(sp);
        testAdv.removeStoryPiece(sp);

        assertFalse("The Adventure should not contain the StoryPiece that was removed from it",
                testAdv.getStoryPieces().contains(sp));
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPieces_GivenTheStoryPieces() {
        Adventure testAdv = new Adventure();
        StoryPiece[] storyPieces = new StoryPiece[5];
        for (int i=0; i<5; i++) {
            storyPieces[i] = new StoryPiece();
            testAdv.addStoryPiece(storyPieces[i]);
        }

        for (StoryPiece sp : storyPieces) {
            testAdv.removeStoryPiece(sp);
            assertFalse("The Adventure should not contain one of the StoryPieces that were removed from it",
                    testAdv.getStoryPieces().contains(sp));
        }
    }

    @Test
    public void RemoveStoryPiece_ShouldNotRemovyStoryPiece_GivenItIsTheLastOne() {
        Adventure testAdv = new Adventure();
        StoryPiece sp = testAdv.getStoryPieces().get(0);
        testAdv.removeStoryPiece(sp);
        assertTrue("The Adventure should not have removed the last StoryPiece it has",
                testAdv.getStoryPieces().contains(sp));
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPiecesExceptForTheLastOne_GivenTheStoryPieces() {
        Adventure testAdv = new Adventure();
        StoryPiece[] storyPieces = new StoryPiece[5];
        storyPieces[0] = testAdv.getStoryPieces().get(0);

        for (int i=1; i<5; i++) {
            storyPieces[i] = new StoryPiece();
            testAdv.addStoryPiece(storyPieces[i]);
        }

        for (StoryPiece sp : storyPieces) {
            testAdv.removeStoryPiece(sp);
            assertTrue("The Adventure should not have removed the last StoryPiece",
                    testAdv.getStoryPieces().size() > 0);
        }
    }


}
