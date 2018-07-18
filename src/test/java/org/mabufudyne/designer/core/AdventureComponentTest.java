package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Random;

@Category(ComponentTest.class)
public class AdventureComponentTest {

    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    @BeforeEach
    protected void createDefaultObjects() {
        defaultAdventure = new Adventure();
        defaultStoryPiece = defaultAdventure.getStoryPieces().get(0);
    }

    @Test
    public void Adventure_ShouldReuseOrderOfRemovedStoryPiecesAndIncrementTheOrderOnlyIfThereAreNoSpareOrdersLeft() {

        // Create StoryPieces with orders 2-6, currently assigned orders will be 1-6
        for (int i=0; i<5; i++) {
            defaultAdventure.createNewStoryPiece();
        }

        StoryPiece removedSP1 = defaultAdventure.getStoryPieces().get(2);
        StoryPiece removedSP2 = defaultAdventure.getStoryPieces().get(4);

        // Removing StoryPieces with orders 3 and 5
        defaultAdventure.removeStoryPiece(removedSP1);
        defaultAdventure.removeStoryPiece(removedSP2);

        // Adventure should reuse the unassigned orders before incrementing the order
        StoryPiece spWithOrder3 = defaultAdventure.createNewStoryPiece();
        StoryPiece spWithOrder5 = defaultAdventure.createNewStoryPiece();

        // At this point all orders 1-6 should be assigned, so we expect this to have order 7
        StoryPiece newSP = defaultAdventure.createNewStoryPiece();

        assertEquals(3, spWithOrder3.getOrder(),
                "Order not reused - StoryPiece should have order 3");
        assertEquals(5, spWithOrder5.getOrder(),
                "Order not reused, StoryPiece should have order 5");
        assertEquals(7, newSP.getOrder(),
                "StoryPiece should have been assigned a new incremented order 7.");

    }

    @Test
    public void Adventure_ShouldKeepStoryPiecesSortedByTheirOrder_GivenThatSomeStoryPiecesAreRemovedAndAdded() {

        StoryPiece spTwo = defaultAdventure.createNewStoryPiece();
        StoryPiece spThree = defaultAdventure.createNewStoryPiece();
        defaultAdventure.createNewStoryPiece();

        // StoryPieces should be in order 1-2-3-4 at the moment
        for (int i=0; i<4; i++) {
            int expectedOrder = i+1;
            int actualOrder = defaultAdventure.getStoryPieces().get(i).getOrder();
            assertEquals(expectedOrder, actualOrder,
                    String.format("The order of the StoryPiece before modifications is {}, expected {}",
                            expectedOrder, actualOrder));
        }

        // Remaining StoryPieces should be in order 1-4 at the moment
        defaultAdventure.removeStoryPiece(spThree);
        defaultAdventure.removeStoryPiece(spTwo);

        defaultAdventure.createNewStoryPiece();
        defaultAdventure.createNewStoryPiece();

        // StoryPieces should be in order 1-2-3-4 after removal/creation of the two StoryPieces, NOT 1-4-2-3
        for (int i=0; i<4; i++) {
            int expectedOrder = i+1;
            int actualOrder = defaultAdventure.getStoryPieces().get(i).getOrder();
            assertEquals(expectedOrder, actualOrder,
                    String.format("The order of the StoryPiece after modifications is {}, expected {}",
                            expectedOrder, actualOrder));
        }

    }



}