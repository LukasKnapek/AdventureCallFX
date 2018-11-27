package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Properties;

class AdventureComponentTest {

    private Application app = new Application(new Properties());
    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    @BeforeEach
    void createDefaultObjects() {
        defaultStoryPiece = new StoryPiece();
        defaultAdventure = new Adventure(app, defaultStoryPiece);
    }

    @Test
    void Adventure_ShouldReuseOrderOfRemovedStoryPiecesAndIncrementTheOrderOnlyIfThereAreNoSpareOrdersLeft() {

        // Create StoryPieces with orders 2-6, currently assigned orders will be 1-6
        for (int i=0; i<5; i++) {
            StoryPiece sp = new StoryPiece();
            defaultAdventure.addStoryPiece(sp);
        }

        // Removing StoryPieces with orders 3 and 5
        defaultAdventure.removeStoryPiece(defaultAdventure.getStoryPieces().get(2));
        defaultAdventure.removeStoryPiece(defaultAdventure.getStoryPieces().get(3));

        // Adventure should reuse the unassigned orders before incrementing the order
        StoryPiece spWithOrder3 = new StoryPiece();
        StoryPiece spWithOrder5 = new StoryPiece();

        defaultAdventure.addStoryPiece(spWithOrder3);
        defaultAdventure.addStoryPiece(spWithOrder5);

        // At this point all orders 1-6 should be assigned, so we expect this to have order 7
        StoryPiece newSP = new StoryPiece();
        defaultAdventure.addStoryPiece(newSP);

        assertEquals(3, spWithOrder3.getOrder(),
                "Order not reused - StoryPiece should have order 3");
        assertEquals(5, spWithOrder5.getOrder(),
                "Order not reused, StoryPiece should have order 5");
        assertEquals(7, newSP.getOrder(),
                "StoryPiece should have been assigned a new incremented order 7.");

    }

    @Test
    void Adventure_ShouldShuffleStoryPiecesSuchThatTheResultIsDifferentAndFixedStoryPieceOrdersAreNotChanged_GivenSomeStoryPiecesAreFixed() {

        StoryPiece SP1NonFixed = defaultStoryPiece;
        StoryPiece SP2NonFixed = new StoryPiece();
        StoryPiece SP3Fixed = new StoryPiece();
        StoryPiece SP4Fixed = new StoryPiece();

        defaultAdventure.addStoryPiece(SP2NonFixed);
        defaultAdventure.addStoryPiece(SP3Fixed);
        defaultAdventure.addStoryPiece(SP4Fixed);

        SP3Fixed.setFixed(true);
        SP4Fixed.setFixed(true);
        int SP3OriginalOrder = SP3Fixed.getOrder();
        int SP4OriginalOrder = SP4Fixed.getOrder();

        String failedShuffleMsg = "Non-fixed StoryPiece order was not shuffled with at least two shuffable StoryPieces.";
        String failedFixedMsg = "Fixed StoryPiece order was different after a shuffle.";


        // Out of four StoryPieces, two are fixed. We expect the other two to switch orders every shuffle
        // while the fixed StoryPieces should keep the same orders
        for (int i=0; i<10; i++) {
            int SP1OriginalOrder = SP1NonFixed.getOrder();
            int SP2OriginalOrder = SP2NonFixed.getOrder();

            defaultAdventure.shuffleStoryPieces();

            assertFalse(SP1NonFixed.getOrder() == SP1OriginalOrder, failedShuffleMsg);
            assertFalse(SP2NonFixed.getOrder() == SP2OriginalOrder, failedShuffleMsg);
            assertEquals(SP3Fixed.getOrder(), SP3OriginalOrder, failedFixedMsg);
            assertEquals(SP4Fixed.getOrder(), SP4OriginalOrder, failedFixedMsg);
        }
    }

}