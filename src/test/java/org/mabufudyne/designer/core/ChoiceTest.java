package org.mabufudyne.designer.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChoiceTest {

    private StoryPiece defaultStoryPiece;

    @BeforeEach
    private void createDefaultStoryPiece() {
        defaultStoryPiece = new StoryPiece();
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

}
