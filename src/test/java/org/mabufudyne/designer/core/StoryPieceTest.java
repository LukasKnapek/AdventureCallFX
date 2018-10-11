package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
        defaultStoryPiece = defaultAdventure.getStoryPieces().get(0);
        StoryPiece choiceSP = defaultAdventure.createNewStoryPiece();
        defaultChoice = new Choice(choiceSP);

    }

    @Test
    public void Title_ShouldBeSimpleStringProperty() {
        assertTrue(defaultStoryPiece.titleProperty() instanceof SimpleStringProperty);
    }

    @Test
    public void SetTitle_ShouldSetTheStoryPieceTitle_GivenTheTitle() {
        String newTitle = "On the verge of death";
        defaultStoryPiece.setTitle(newTitle);
        assertEquals(newTitle, defaultStoryPiece.getTitle(),
                "StoryPiece did not have the title that was assigned to it.");
    }

    @Test
    public void SetTitle_ShouldPerformAfterTaskOperationsAfterSettingTheTitle() {
        Application.getApp().resetStateHistory();
        defaultStoryPiece.setTitle("New title");

        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    public void SetStory_ShouldSetTheStoryPieceStory_GivenTheStory() {
        String story = "Once upon a time, there was a vast and a beautiful kingdom.";
        defaultStoryPiece.setStory(story);
        assertEquals(story, defaultStoryPiece.getStory(),
                "StoryPiece did not have the story that was given to it.");
    }

    @Test
    public void SetStory_ShouldPerformAfterTaskOperationsAfterSettingTheStory() {
        Application.getApp().resetStateHistory();
        defaultStoryPiece.setStory("New story");

        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    public void SetColor_ShouldSetTheStoryPieceColor_GivenTheColor() {
        String newColor = "FF0000";
        defaultStoryPiece.setColor(newColor);

        assertEquals(newColor, defaultStoryPiece.getColor(),
                "StoryPiece did not have the color that was assigned to it.");
    }

    @Test
    public void SetColor_ShouldPerformAfterTaskOperationsAfterSettingTheColor() {
        Application.getApp().resetStateHistory();

        defaultStoryPiece.setColor("FFFF00");

        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    public void Fixed_ShouldBeSimpleBooleanProperty() {
        assertTrue(defaultStoryPiece.fixedProperty() instanceof SimpleBooleanProperty);
    }

    @Test
    public void SetFixed_ShouldPerformAfterTaskOperationsAfterSettingTheFixedStatus() {
        Application.getApp().resetStateHistory();

        defaultStoryPiece.setFixed(true);

        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    public void Order_ShouldBeSimpleIntegerProperty() {
        assertTrue(defaultStoryPiece.orderProperty() instanceof SimpleIntegerProperty);
    }

    @Test
    public void SetOrder_ShouldPerformAfterTaskOperationsAfterSettingTheOrder() {
        defaultAdventure.createNewStoryPiece();

        Application.getApp().resetStateHistory();
        defaultStoryPiece.setOrder(2);

        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    public void Choices_IsAnObservableList() {
        assertTrue(defaultStoryPiece.getChoices() instanceof ObservableList);
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
        Choice choiceOne = new Choice(sp, "Go to");
        Choice choiceTwo = new Choice(sp, "Walk to");
        defaultStoryPiece.addChoice(choiceOne);
        defaultStoryPiece.addChoice(choiceTwo);
        assertTrue(defaultStoryPiece.getChoices().contains(choiceOne) &&
                !defaultStoryPiece.getChoices().contains(choiceTwo),
                "StoryPiece should not add a Choice with the same StoryPiece that another existing Choice points to.");
    }

    @Test
    public void AddChoice_ShouldPerformAfterTaskOperationsAfterSettingTheOrder() {
        Application.getApp().resetStateHistory();

        defaultStoryPiece.addChoice(defaultChoice);

        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    public void RemoveChoice_ShouldRemoveChoiceFromStoryPiece_GivenTheChoice() {
        defaultStoryPiece.addChoice(defaultChoice);
        defaultStoryPiece.removeChoice(defaultChoice);
        assertEquals(0, defaultStoryPiece.getChoices().size(),
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

        assertEquals(0, defaultStoryPiece.getChoices().size(),
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

    @Test
    public void RemoveChoice_ShouldPerformAfterTaskOperationsAfterRemovingTheChoice() {
        defaultStoryPiece.addChoice(defaultChoice);
        Application.getApp().resetStateHistory();

        defaultStoryPiece.removeChoice(defaultChoice);
        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    public void EqualsHashCode_ShouldBeReflexive() {
        StoryPiece sp = defaultStoryPiece;

        assertEquals(sp, sp,
                "A StoryPiece is not considered equal to itself.");
        assertEquals(sp.hashCode(), sp.hashCode(),
                "A StoryPiece hashcode is not reflexive.");
    }

    @Test
    public void EqualsHashCode_ShouldBeSymmetric() {
        StoryPiece sp1 = defaultStoryPiece;
        StoryPiece sp2 = defaultAdventure.getStoryPieces().get(0);

        assertEquals(sp1, sp2,
                "Two same StoryPieces are not considered equal.");
        assertEquals(sp1.hashCode(), sp2.hashCode(),
                "Two same StoryPieces have different hash codes.");
    }

    @Test
    public void EqualsHashCode_ShouldBeTransitive() {
        StoryPiece sp1 = defaultStoryPiece;
        StoryPiece sp2 = defaultAdventure.getStoryPieces().get(0);
        StoryPiece sp3 = defaultAdventure.getStoryPieces().get(0);

        assertTrue(sp1.equals(sp2) &&
                        sp2.equals(sp3) &&
                        sp1.equals(sp3),
                "The transitive property does not apply to three equal StoryPieces.");
        assertTrue(sp1.hashCode() == sp2.hashCode() &&
                        sp2.hashCode() == sp3.hashCode() &&
                        sp1.hashCode() == sp3.hashCode(),
                "The transitive property doest not apply to hash codes of three equal StoryPieces.");
    }

    /** Helpers **/

    private static void WereAfterTasksPerformedCorrectly(int expectedSavedStates) {
        Application app = Application.getApp();

        // If there are X saved states, there should be X saved states in the undo history
        assertEquals(app.getUndoList().size(), expectedSavedStates,
                String.format("Expected %s new saved state(s) after the operation, there are %s instead",
                        expectedSavedStates, app.getUndoList().size()));

    }
}
