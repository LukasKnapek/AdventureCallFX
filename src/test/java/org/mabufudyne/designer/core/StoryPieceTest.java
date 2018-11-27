package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

class StoryPieceTest {

    private Application app = new Application(new Properties());
    private Application mockedApp;

    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    @BeforeEach
    void createDefaultStoryPiece() {
        defaultStoryPiece = new StoryPiece();
        defaultAdventure = new Adventure(app, defaultStoryPiece);
        mockedApp = mock(Application.class);
    }

    @Test
    void Title_ShouldBeSimpleStringProperty() {
        assertTrue(defaultStoryPiece.titleProperty() instanceof SimpleStringProperty);
    }

    @Test
    void SetTitle_ShouldSetTheStoryPieceTitle_GivenTheTitle() {
        String newTitle = "On the verge of death";
        defaultStoryPiece.setTitle(newTitle);
        assertEquals(newTitle, defaultStoryPiece.getTitle(),
                "StoryPiece did not have the title that was assigned to it.");
    }

    @Test
    void SetTitle_ShouldPerformAfterTaskOperationsAfterSettingTheTitle() {
        defaultAdventure.setParentApp(mockedApp);

        defaultStoryPiece.setTitle("New title");
        verify(mockedApp).performAfterTaskActions();
    }

    @Test
    void SetStory_ShouldSetTheStoryPieceStory_GivenTheStory() {
        String story = "Once upon a time, there was a vast and a beautiful kingdom.";
        defaultStoryPiece.setStory(story);
        assertEquals(story, defaultStoryPiece.getStory(),
                "StoryPiece did not have the story that was given to it.");
    }

    @Test
    void SetStory_ShouldPerformAfterTaskOperationsAfterSettingTheStory() {
        defaultAdventure.setParentApp(mockedApp);

        defaultStoryPiece.setStory("New story");
        verify(mockedApp).performAfterTaskActions();
    }

    @Test
    void SetColor_ShouldSetTheStoryPieceColor_GivenTheColor() {
        String newColor = "FF0000";
        defaultStoryPiece.setColor(newColor);

        assertEquals(newColor, defaultStoryPiece.getColor(),
                "StoryPiece did not have the color that was assigned to it.");
    }

    @Test
    void SetColor_ShouldPerformAfterTaskOperationsAfterSettingTheColor() {
        defaultAdventure.setParentApp(mockedApp);

        defaultStoryPiece.setColor("FFFF00");
        verify(mockedApp).performAfterTaskActions();
    }

    @Test
    void Fixed_ShouldBeSimpleBooleanProperty() {
        assertTrue(defaultStoryPiece.fixedProperty() instanceof SimpleBooleanProperty);
    }

    @Test
    void SetFixed_ShouldPerformAfterTaskOperationsAfterSettingTheFixedStatus() {
        defaultAdventure.setParentApp(mockedApp);

        defaultStoryPiece.setFixed(true);
        verify(mockedApp).performAfterTaskActions();
    }

    @Test
    void Order_ShouldBeSimpleIntegerProperty() {
        assertTrue(defaultStoryPiece.orderProperty() instanceof SimpleIntegerProperty);
    }

    @Test
    void SetOrder_ShouldPerformAfterTaskOperationsAfterSettingTheOrder() {
        StoryPiece sp = new StoryPiece();
        defaultAdventure.addStoryPiece(sp);
        defaultAdventure.setParentApp(mockedApp);

        defaultStoryPiece.setOrder(2);
        verify(mockedApp).performAfterTaskActions();
    }

    @Test
    void Choices_IsAnObservableList() {
        assertTrue(defaultStoryPiece.getChoices() instanceof ObservableList);
    }

    @Test
    void AddChoice_ShouldAddChoice_GivenTheChoice() {
        Choice choice = new Choice(new StoryPiece());
        defaultStoryPiece.addChoice(choice);
        assertTrue(defaultStoryPiece.getChoices().contains(choice),
                "StoryPiece should contain a Choice that was passed to it.");
    }

    @Test
    void AddChoice_ShouldNotAddChoice_GivenTheStoryPieceAlreadyContainsTheChoice() {
        Choice choice = new Choice(new StoryPiece());

        defaultStoryPiece.addChoice(choice);
        defaultStoryPiece.addChoice(choice);
        assertTrue(Collections.frequency(defaultStoryPiece.getChoices(), choice) == 1,
                "StoryPiece should not add a Choice if it already contains that Choice.");
    }

    @Test
    void AddChoice_ShouldNotAddAChoice_GivenTheChoiceIsTheStoryPieceItself() {
        Choice selfChoice = new Choice(defaultStoryPiece);
        defaultStoryPiece.addChoice(selfChoice);
        assertFalse(defaultStoryPiece.getChoices().contains(selfChoice),
                "StoryPiece should not add a Choice that is the StoryPiece itself.");
    }

    @Test
    void AddChoice_ShouldNotAddAChoice_GivenStoryPieceContainsAnotherChoiceWithTheSameStoryPiece() {
        StoryPiece sp = new StoryPiece();
        defaultAdventure.addStoryPiece(sp);

        Choice choiceOne = new Choice(sp, "Go to");
        Choice choiceTwo = new Choice(sp, "Walk to");
        defaultStoryPiece.addChoice(choiceOne);
        defaultStoryPiece.addChoice(choiceTwo);

        assertTrue(defaultStoryPiece.getChoices().contains(choiceOne) &&
                !defaultStoryPiece.getChoices().contains(choiceTwo),
                "StoryPiece should not add a Choice with the same StoryPiece that another existing Choice points to.");
    }

    @Test
    void AddChoice_ShouldPerformAfterTaskOperationsAfterPerformingTheAction() {
        defaultAdventure.setParentApp(mockedApp);

        Choice choice = new Choice(new StoryPiece());
        defaultStoryPiece.addChoice(choice);
        verify(mockedApp).performAfterTaskActions();
    }

    @Test
    void RemoveChoice_ShouldRemoveChoiceFromStoryPiece_GivenTheChoice() {
        Choice choice = new Choice(new StoryPiece());

        defaultStoryPiece.addChoice(choice);
        defaultStoryPiece.removeChoice(choice);
        assertEquals(0, defaultStoryPiece.getChoices().size(),
                "The only Choice that was added to the StoryPiece should have been removed.");
    }

    @Test
    void RemoveChoice_ShouldRemoveChoicesFromStoryPiece_GivenTheChoices() {
        Choice[] choices = new Choice[2];

        for (int i=0; i<2; i++) {
            choices[i] = new Choice(new StoryPiece());
            defaultStoryPiece.addChoice(choices[i]);
        }

        for (Choice choice : choices) {
            defaultStoryPiece.removeChoice(choice);
        }

        assertEquals(0, defaultStoryPiece.getChoices().size(),
                "All Choices that were added to the StoryPiece should have been removed.");
    }

    @Test
    void RemoveChoice_ThrowsExceptionWhenTryingToRemoveChoice_GivenStoryPieceDoesNotContainTheChoice() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            defaultStoryPiece.removeChoice(new Choice(new StoryPiece()));
        });
        assertEquals("StoryPiece does not contain the given Choice.", ex.getMessage(),
                "The exception did not have the correct message.");
    }

    @Test
    void RemoveChoice_ShouldRemoveOnlySpecificChoices_GivenTheChoices() {
        // Add 10 choices to a StoryPiece, remove random number of them, check that the rest still remains
        ArrayList<Choice> choices = new ArrayList<>();

        for (int i=0; i<10; i++) {
            Choice choice = new Choice(new StoryPiece());
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
    void RemoveChoice_ShouldPerformAfterTaskOperationsAfterRemovingTheChoice() {
        Choice choice = new Choice(new StoryPiece());
        defaultStoryPiece.addChoice(choice);

        defaultAdventure.setParentApp(mockedApp);

        defaultStoryPiece.removeChoice(choice);
        verify(mockedApp).performAfterTaskActions();

    }

    @Test
    void EqualsHashCode_ShouldBeReflexive() {
        StoryPiece sp = defaultStoryPiece;

        assertEquals(sp, sp,
                "A StoryPiece is not considered equal to itself.");
        assertEquals(sp.hashCode(), sp.hashCode(),
                "A StoryPiece hashcode is not reflexive.");
    }

    @Test
    void EqualsHashCode_ShouldBeSymmetric() {
        StoryPiece sp1 = defaultStoryPiece;
        StoryPiece sp2 = defaultAdventure.getStoryPieces().get(0);

        assertEquals(sp1, sp2,
                "Two same StoryPieces are not considered equal.");
        assertEquals(sp1.hashCode(), sp2.hashCode(),
                "Two same StoryPieces have different hash codes.");
    }

    @Test
    void EqualsHashCode_ShouldBeTransitive() {
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
}
