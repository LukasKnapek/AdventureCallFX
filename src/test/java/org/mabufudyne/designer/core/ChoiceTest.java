package org.mabufudyne.designer.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChoiceTest {

    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    @BeforeEach
    private void createDefaultStoryPiece() {
        defaultAdventure = new Adventure();
        defaultStoryPiece = defaultAdventure.createNewStoryPiece();
    }

    @Test
    public void Constructor_ShouldCreateAChoiceObjectWithStoryPieceAndName_GivenTheStoryPieceAndName() {
        Choice choice = new Choice(defaultStoryPiece, "Move 5 steps to");
        assertEquals("Move 5 steps to", choice.getText(),
                "Choice did not have the text that it was assigned.");
        assertEquals(defaultStoryPiece, choice.getStoryPiece(),
                "Choice did not have the StoryPiece that it was assigned.");
    }

    @Test
    public void Constructor_ShouldCreateAChoiceObjectWithStoryPieceAndDefaultName_GivenTheStoryPieceAndNoName() {
        Choice choice = new Choice(defaultStoryPiece);
        assertEquals(Choice.getDefaultText(), choice.getText(),
                "Choice did not have the default text when no text was passed to it.");
    }

    @Test
    public void EqualsHashCode_ShouldBeReflexive() {
        Choice ch1 = new Choice(defaultStoryPiece, "Walk to");

        assertEquals(ch1, ch1,
                "A Choice is not considered equal to itself.");
        assertEquals(ch1.hashCode(), ch1.hashCode(),
                "A Choice hashcode is not reflexive.");
    }

    @Test
    public void EqualsHashCode_ShouldBeSymmetric() {
        Choice ch1 = new Choice(defaultStoryPiece, "Walk to");
        Choice ch2 = new Choice(defaultStoryPiece, "Walk to");

        assertEquals(ch1, ch2,
                "Two same Choices are not considered equal.");
        assertEquals(ch1.hashCode(), ch2.hashCode(),
                "Two same Choices have different hash codes.");
    }

    @Test
    public void EqualsHashCode_ShouldBeTransitive() {
        Choice ch1 = new Choice(defaultStoryPiece, "Walk to");
        Choice ch2 = new Choice(defaultStoryPiece, "Walk to");
        Choice ch3 = new Choice(defaultStoryPiece, "Walk to");

        assertTrue(ch1.equals(ch2) &&
                        ch2.equals(ch3) &&
                        ch1.equals(ch3),
                "The transitive property does not apply to three equal Choices.");
        assertTrue(ch1.hashCode() == ch2.hashCode() &&
                    ch2.hashCode() == ch3.hashCode() &&
                    ch1.hashCode() == ch3.hashCode(),
                "The transitive property doest not apply to hash codes of three equal Choices.");
    }



}
