package org.mabufudyne.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StoryPieceTest {

    private StoryPiece defaultStoryPiece;

    @BeforeEach
    private void createDefaultStoryPiece() {
        defaultStoryPiece = new StoryPiece();
    }

    @Test
    public void Constructor_ShouldCreateAStoryPieceObjectWithTitleAndBlankStory_GivenTheTitle() {
        StoryPiece testSP = new StoryPiece("StoryPiece Title");
        assertEquals("StoryPiece Title", testSP.getTitle(),
                "The StoryPiece Title should be the same it has been given");
        assertEquals("", testSP.getStory(),
                "The StoryPiece Story should be left blank by default");
    }

    @Test
    public void Constructor_ShouldCreateAStoryPieceObjectWithDefaultTitleAndBlankStory_GivenNoArguments() {
        assertEquals(StoryPiece.getDefaultTitle(), defaultStoryPiece.getTitle(),
                "The StoryPiece Title should be default");
        assertEquals("", defaultStoryPiece.getStory(),
                "The StoryPiece Story should be blank");
    }

    @Test
    public void Constructor_ShouldIncrementOrderForNewStoryPiecesFromOne_GivenNoStoryPiecesExist() {
        StoryPiece.setNextOrder(1);

        for (int i=1; i<= 5; i++) {
            StoryPiece sp = new StoryPiece();
            assertEquals(i, sp.getOrder(),
                    String.format("The {0}th StoryPiece should have order {0}.", i));
        }
    }

    @Test
    public void AddChoice_ShouldAddStoryPieceAsAChoice_GivenTheStoryPiece() {
        StoryPiece choice = new StoryPiece();
        defaultStoryPiece.addChoice(choice);
        assertTrue(defaultStoryPiece.getChoices().contains(choice),
                "StoryPiece should contain a choice StoryPiece that was passed to it.");
    }

    @Test
    public void AddChoice_ShouldNotAddStoryPieceAsAChoice_GivenTheStoryPieceIsAlreadyAChoice() {
        StoryPiece choice = new StoryPiece();
        defaultStoryPiece.addChoice(choice);
        defaultStoryPiece.addChoice(choice);
        assertTrue(Collections.frequency(defaultStoryPiece.getChoices(), choice) == 1,
                "StoryPiece should not add a choice if it already contains that choice.");
    }

    @Test
    public void AddChoice_ShouldNotAddStoryPieceAsAChoice_GivenTheChoiceIsTheStoryPieceItself() {
        defaultStoryPiece.addChoice(defaultStoryPiece);
        assertFalse(defaultStoryPiece.getChoices().contains(defaultStoryPiece),
                "StoryPiece should not add itself as a choice.");
    }

    @Test
    public void RemoveChoice_ShouldRemoveChoiceFromStoryPiece_GivenTheChoice() {
        StoryPiece choice = new StoryPiece();
        defaultStoryPiece.addChoice(choice);
        defaultStoryPiece.removeChoice(choice);
        assertTrue(defaultStoryPiece.getChoices().size() == 0,
                "The only choice that was added to the StoryPiece should have been removed.");
    }

    @Test
    public void RemoveChoice_ShouldRemoveChoicesFromStoryPiece_GivenTheChoices() {
        StoryPiece[] choices = new StoryPiece[5];

        for (int i=0; i<5; i++) {
            choices[i] = new StoryPiece();
            defaultStoryPiece.addChoice(choices[i]);
        }

        for (StoryPiece choice : choices) {
            defaultStoryPiece.removeChoice(choice);
        }

        assertTrue(defaultStoryPiece.getChoices().size() == 0,
                "All choices that were added to the StoryPiece should have been removed.");
    }

    @Test
    public void RemoveChoice_ThrowsExceptionWhenTryingToRemoveChoice_GivenStoryPieceDoesNotContainTheChoice() {
        StoryPiece choice = new StoryPiece();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            defaultStoryPiece.removeChoice(choice);
        });
        assertEquals("StoryPiece does not contain the given choice.", ex.getMessage(),
                "The exception did not have the correct message.");
    }

    @Test
    public void RemoveChoice_ShouldRemoveOnlySpecificChoices_GivenTheChoices() {
        // Add 10 choices to a StoryPiece, remove random number of them, check that the rest still remains
        ArrayList<StoryPiece> choices = new ArrayList<>();

        for (int i=0; i<10; i++) {
            StoryPiece choice = new StoryPiece();
            choices.add(choice);
            defaultStoryPiece.addChoice(choice);
        }

        Random rand = new Random();
        int numOfChosen = rand.nextInt(10);

        for (int i=0; i<numOfChosen; i++) {
            int randIndex = rand.nextInt(choices.size()-1);
            StoryPiece removedChoice = choices.remove(randIndex);
            defaultStoryPiece.removeChoice(removedChoice);
        }

        for (StoryPiece remainingChoice : choices) {
            assertTrue(defaultStoryPiece.getChoices().contains(remainingChoice),
                    "StoryPiece no longer contains a choice that was not supposed to be removed.");
        }
    }

}
