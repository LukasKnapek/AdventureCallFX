package org.mabufudyne.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

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

}
