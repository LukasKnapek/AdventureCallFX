package org.mabufudyne.core;

import static org.junit.Assert.*;
import org.junit.Test;

public class StoryPieceTest {
    @Test
    public void Constructor_ShouldCreateAStoryPieceObjectWithTitleAndBlankStory_GivenTheTitle() {
        StoryPiece testSP = new StoryPiece("StoryPiece Title");
        assertEquals("The StoryPiece Title should be the same it has been given",
                "StoryPiece Title", testSP.getTitle());
        assertEquals("The StoryPiece Story should be left blank by default",
                "", testSP.getStory());
    }

    @Test
    public void Constructor_ShouldCreateAStoryPieceObjectWithDefaultTitleAndBlankStory_GivenNoArguments() {
        StoryPiece defaultSP = new StoryPiece();
        assertEquals("The StoryPiece Title should be default",
                StoryPiece.getDefaultTitle(), defaultSP.getTitle());
        assertEquals("The StoryPiece Story should be blank",
                "", defaultSP.getStory());
    }

    @Test
    public void Constructor_ShouldIncrementOrderForNewStoryPiecesFromOne_GivenNoStoryPiecesExist() {
        StoryPiece.setNextOrder(1);

        for (int i=1; i<= 5; i++) {
            StoryPiece sp = new StoryPiece();
            assertEquals(String.format("The {0}th StoryPiece should have order {0}.", i),
                    i, sp.getOrder());
        }
    }

}
