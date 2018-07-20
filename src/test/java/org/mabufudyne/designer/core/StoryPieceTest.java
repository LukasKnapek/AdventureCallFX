package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StoryPieceTest {

    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;
    private Choice defaultChoice;

    @BeforeEach
    private void createDefaultStoryPiece() {
        defaultAdventure = new Adventure();
        defaultStoryPiece = defaultAdventure.createNewStoryPiece();
        StoryPiece choiceSP = defaultAdventure.createNewStoryPiece();
        defaultChoice = new Choice(choiceSP);

    }

    @Test
    public void SetStory_ShouldSetTheStoryPieceStory_GivenTheStory() {
        String story = "Once upon a time, there was a vast and a beautiful kingdom.";
        defaultStoryPiece.setStory(story);
        assertEquals(story, defaultStoryPiece.getStory(),
                "The StoryPiece did not have the story that was given to it.");
    }

    @Test
    public void SetColor_ShouldSetTheStoryPieceColor_GivenTheColor() {
        Color newColor = Color.RED;
        defaultStoryPiece.setColor(newColor);

        assertEquals(Color.RED, defaultStoryPiece.getColor(),
                "The StoryPiece did not have the color that was assigned to it.");
    }

    @Test
    public void AddChoice_ShouldAddChoice_GivenTheChoice() {
        defaultStoryPiece.addChoice(defaultChoice);
        assertTrue(defaultStoryPiece.getChoices().contains(defaultChoice),
                "StoryPiece should contain a Choice that was passed to it.");
    }

    @Test
    public void AddChoice_ShouldNotAddChoice_GivenTheStoryPieceAlreadyContainsTheChoice() {
        defaultStoryPiece.addChoice(defaultChoice);
        defaultStoryPiece.addChoice(defaultChoice);
        assertTrue(Collections.frequency(defaultStoryPiece.getChoices(), defaultChoice) == 1,
                "StoryPiece should not add a Choice if it already contains that Choice.");
    }

    @Test
    public void AddChoice_ShouldNotAddAChoice_GivenTheChoiceIsTheStoryPieceItself() {
        Choice selfChoice = new Choice(defaultStoryPiece);
        defaultStoryPiece.addChoice(selfChoice);
        assertFalse(defaultStoryPiece.getChoices().contains(selfChoice),
                "StoryPiece should not add a Choice that is the StoryPiece itself.");
    }

    @Test
    public void AddChoice_ShouldNotAddAChoice_GivenStoryPieceContainsAnotherChoiceWithTheSameStoryPiece() {
        StoryPiece sp = defaultAdventure.createNewStoryPiece();
        Choice choiceOne = new Choice(sp);
        Choice choiceTwo = new Choice(sp);
        defaultStoryPiece.addChoice(choiceOne);
        defaultStoryPiece.addChoice(choiceTwo);
        assertTrue(defaultStoryPiece.getChoices().contains(choiceOne) &&
                !defaultStoryPiece.getChoices().contains(choiceTwo),
                "StoryPiece should not add a Choice with the same StoryPiece that another existing Choice points to.");
    }

    @Test
    public void RemoveChoice_ShouldRemoveChoiceFromStoryPiece_GivenTheChoice() {
        defaultStoryPiece.addChoice(defaultChoice);
        defaultStoryPiece.removeChoice(defaultChoice);
        assertTrue(defaultStoryPiece.getChoices().size() == 0,
                "The only Choice that was added to the StoryPiece should have been removed.");
    }

    @Test
    public void RemoveChoice_ShouldRemoveChoicesFromStoryPiece_GivenTheChoices() {
        Choice[] choices = new Choice[5];

        for (int i=0; i<5; i++) {
            choices[i] = new Choice(defaultAdventure.createNewStoryPiece());
            defaultStoryPiece.addChoice(choices[i]);
        }

        for (Choice choice : choices) {
            defaultStoryPiece.removeChoice(choice);
        }

        assertTrue(defaultStoryPiece.getChoices().size() == 0,
                "All Choices that were added to the StoryPiece should have been removed.");
    }

    @Test
    public void RemoveChoice_ThrowsExceptionWhenTryingToRemoveChoice_GivenStoryPieceDoesNotContainTheChoice() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            defaultStoryPiece.removeChoice(defaultChoice);
        });
        assertEquals("StoryPiece does not contain the given Choice.", ex.getMessage(),
                "The exception did not have the correct message.");
    }

    @Test
    public void RemoveChoice_ShouldRemoveOnlySpecificChoices_GivenTheChoices() {
        // Add 10 choices to a StoryPiece, remove random number of them, check that the rest still remains
        ArrayList<Choice> choices = new ArrayList<>();

        for (int i=0; i<10; i++) {
            Choice choice = new Choice(defaultAdventure.createNewStoryPiece());
            choices.add(choice);
            defaultStoryPiece.addChoice(choice);
        }

        Random rand = new Random();
        int numOfChosen = rand.nextInt(10);

        for (int i=0; i<numOfChosen; i++) {
            int randIndex = rand.nextInt(choices.size()-1);
            Choice removedChoice = choices.remove(randIndex);
            defaultStoryPiece.removeChoice(removedChoice);
        }

        for (Choice remainingChoice : choices) {
            assertTrue(defaultStoryPiece.getChoices().contains(remainingChoice),
                    "StoryPiece no longer contains a Choice that was not supposed to be removed.");
        }
    }
}
