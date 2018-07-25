package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class AdventureTest {

    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    @BeforeEach
    protected void createDefaultObjects() {
        defaultAdventure = new Adventure();
        defaultStoryPiece = defaultAdventure.getStoryPieces().get(0);
    }

    @Test
    public void Constructor_ShouldCreateAnAdventureWithName_GivenTheName() {
        Adventure testAdv = new Adventure("My First Adventure");
        assertEquals("My First Adventure", testAdv.getName(),
                "Name of the Adventure should be equal to the name that was passed to the constructor");
    }

    @Test
    public void Constructor_ShouldCreateAnAdventureWithDefaultName_GivenNoName() {
        assertEquals(Adventure.getDefaultName(), defaultAdventure.getName(),
                "The Adventure name should be the default name");
    }

    @Test
    public void Constructor_ShouldCreateAnAdventureWithOneStoryPiece() {
        assertEquals(1, defaultAdventure.getStoryPieces().size(),
                "The Adventure has to contain at least one StoryPiece at all times," +
                        " including immediately after creation");
    }

    @Test
    public void CreateNewStoryPiece_ShouldCreateNewStoryPieceWithFixedStatusFalseByDefault() {
        assertFalse(defaultStoryPiece.isFixed(),
                "StoryPieces should be created with Fixed status set to false by default.");
    }

    @Test
    public void CreateNewStoryPiece_ShouldCreateNewStoryPieceWithTitle_GivenTheName() {
        String spTitle = "Beginning";
        StoryPiece newSP = defaultAdventure.createNewStoryPiece("Beginning");
        assertEquals(spTitle, newSP.getTitle(),
                "The new StoryPiece does not have the title that was passed to the method.");
    }

    @Test
    public void CreateNewStoryPiece_ShouldCreateNewStoryPieceWithDefaultTitle_GivenNothing() {
        StoryPiece newSP = defaultAdventure.createNewStoryPiece();
        assertEquals(Adventure.getDefaultStoryPieceTitle(), newSP.getTitle(),
                "The new StoryPiece does not have the default title.");
    }

    @Test
    public void CreateNewStoryPiece_ShouldCreateAStoryPieceWithDefaultColorSetToNone() {
        assertEquals(null, defaultStoryPiece.getColor(),
                "StoryPiece should have no color assigned by default.");
    }

    @Test
    public void CreateNewStoryPiece_ShouldCreateAndAddStoryPieceToTheAdventure() {
        StoryPiece newSP = defaultAdventure.createNewStoryPiece();
        assertTrue(defaultAdventure.getStoryPieces().contains(newSP),
                "The Adventure does not contain the StoryPiece that was created inside it");
    }

    @Test
    public void CreateNewStoryPiece_ShouldIncrementOrderOfEveryNewStoryPiece() {
        assertEquals(1, defaultStoryPiece.getOrder(),
                "The initial StoryPiece in an Adventure should have order 1.");
        StoryPiece newSP = defaultAdventure.createNewStoryPiece();
        assertEquals(2, newSP.getOrder(),
                "The second StoryPiece in an Adventure should have order 2.");
    }

    @Test
    public void CreateNewStoryPiece_ShouldCreateAndAddStoryPiecesToTheAdventure_GivenMultipleStoryPieces() {
        StoryPiece[] storyPieces = new StoryPiece[5];
        for (int i=0; i<5; i++) {
            storyPieces[i] = defaultAdventure.createNewStoryPiece();
        }

        for (StoryPiece sp : storyPieces) {
            assertTrue(defaultAdventure.getStoryPieces().contains(sp),
                    "The Adventure does not contain one of the StoryPieces that were created inside it");
        }
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPieceFromAdventure_GivenTheStoryPiece() {
        StoryPiece newSP = defaultAdventure.createNewStoryPiece();
        defaultAdventure.removeStoryPiece(newSP);

        assertFalse(defaultAdventure.getStoryPieces().contains(newSP),
                "The Adventure should not contain the StoryPiece that was removed from it");
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPiecesFromAdventure_GivenTheStoryPieces() {
        StoryPiece[] storyPieces = new StoryPiece[5];

        for (int i=0; i<5; i++) {
            storyPieces[i] = defaultAdventure.createNewStoryPiece();
        }

        for (StoryPiece sp : storyPieces) {
            defaultAdventure.removeStoryPiece(sp);
            assertFalse(defaultAdventure.getStoryPieces().contains(sp),
                    "The Adventure should not contain one of the StoryPieces that were removed from it");
        }
    }

    @Test
    public void RemoveStoryPiece_ShouldNotRemoveStoryPieceFromAdventure_GivenItIsTheLastOne() {
        StoryPiece sp = defaultAdventure.getStoryPieces().get(0);
        defaultAdventure.removeStoryPiece(sp);
        assertTrue(defaultAdventure.getStoryPieces().contains(sp),
                "The Adventure should not have removed the last StoryPiece it has");
    }

    @Test
    public void RemoveStoryPiece_ShouldRemoveStoryPiecesFromAdventureExceptForTheLastOne_GivenTheStoryPieces() {
        StoryPiece[] storyPieces = new StoryPiece[5];
        storyPieces[0] = defaultAdventure.getStoryPieces().get(0);

        for (int i=1; i<5; i++) {
            storyPieces[i] = defaultAdventure.createNewStoryPiece();
        }

        for (StoryPiece sp : storyPieces) {
            defaultAdventure.removeStoryPiece(sp);
        }

        assertTrue(defaultAdventure.getStoryPieces().size() > 0,
                "The Adventure should not have removed its last StoryPiece");
    }

    @Test
    public void RemoveStoryPiece_ShouldOnlyRemoveSpecificStoryPiecesFromAdventure_GivenTheStoryPieces() {
        // Create 10 StoryPieces inside an Adventure, remove random number of them, see if the rest remains
        ArrayList<StoryPiece> spList = new ArrayList<>();

        for (int i=0; i<10; i++) {
            spList.add(defaultAdventure.createNewStoryPiece());
        }

        Random rand = new Random();
        int numOfChosen = rand.nextInt(10);

        for (int i=0; i<numOfChosen; i++) {
            int randIndex = rand.nextInt(spList.size()-1);
            StoryPiece removedSP = spList.remove(randIndex);
            defaultAdventure.removeStoryPiece(removedSP);
        }

        for (StoryPiece remainingSP : spList) {
            assertTrue(defaultAdventure.getStoryPieces().contains(remainingSP),
                    "Adventure no longer contains a StoryPiece that was not supposed to be removed.");
        }
    }

    @Test
    public void SwitchStoryPieceOrder_ShouldSwitchTheOrderOfTheStoryPieces_GivenTheFirstStoryPieceAndItsNewOrder() {

        for (int i=0; i<5; i++) {
            defaultAdventure.createNewStoryPiece();
        }

        StoryPiece sp1 = defaultAdventure.getStoryPieces().get(2);
        StoryPiece sp2 = defaultAdventure.getStoryPieces().get(4);
        int sp1Order = sp1.getOrder();
        int sp2Order = sp2.getOrder();

        defaultAdventure.switchStoryPieceOrder(sp1, sp2Order);

        assertEquals(sp1Order, sp2.getOrder(),
                "The second StoryPiece should have the order of the first StoryPiece.");
        assertEquals(sp2Order, sp1.getOrder(),
                "The first StoryPiece should have the order of the second StoryPiece.");
    }

    @Test
    public void SwitchStoryPieceOrder_ShouldNotSwitchTheOrderOfTheStoryPieces_GivenAStoryPieceAndItsCurrentOrderAsTheNewOrder() {

        StoryPiece newSP = defaultAdventure.createNewStoryPiece();
        int currentOrder = newSP.getOrder();

        defaultAdventure.switchStoryPieceOrder(newSP, currentOrder);
        assertEquals(currentOrder, newSP.getOrder(),
                "The StoryPiece order was changed although its new order argument was its current order.");
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, 100 })
    public void SwitchStoryPieceOrder_ShouldThrowAnException_GivenANewOrderThatIsOutsideTheRange1ToTheNumberOfExistingStoryPieces(int order) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            defaultAdventure.switchStoryPieceOrder(defaultStoryPiece, order);
        });
        assertEquals("The requested new order is out of range (1-Number of existing StoryPieces).", ex.getMessage(),
                "The exception did not have the correct message.");
    }

    @Test
    public void ShuffleStoryPieceOrder_ShouldRandomizeTheOrderOfStoryPiecesInAnAdventure() {

        for (int i=0; i<5; i++) {
            defaultAdventure.createNewStoryPiece();
        }

        ArrayList<StoryPiece> storyPiecesOriginalOrder = new ArrayList<>(defaultAdventure.getStoryPieces());
        defaultAdventure.shuffleStoryPieces();

        assertFalse(storyPiecesOriginalOrder.equals(defaultAdventure.getStoryPieces()),
                "The order of the StoryPieces has not changed after shuffle.");
    }

    @Test
    public void ShuffleStoryPieceOrder_ShouldNotResultInTheSameOrderAsBefore_GivenThereAreAtLeastTwoShuffableStoryPieces() {

        // Create a new StoryPiece so we have 2 in total
        defaultAdventure.createNewStoryPiece();

        // 50% chance of the correct order after shuffle even if the method does not work correctly, so repeat 10 times
        // to reduce the probability that the order gets shuffled correctly each time by chance to ~0.09%
        for (int i=0; i<10; i++) {
            ArrayList<StoryPiece> storyPiecesOriginalOrder = new ArrayList<>(defaultAdventure.getStoryPieces());
            defaultAdventure.shuffleStoryPieces();
            assertFalse(storyPiecesOriginalOrder.equals(defaultAdventure.getStoryPieces()),
                    "The order of the StoryPieces has not changed after shuffle even though there are two" +
                            "shuffable StoryPieces.");
        }
    }

    @Test
    public void ShuffleStoryPieceOrder_ShouldNotShuffleStoryPiecesWithFixedStatus() {
        for (int i=0; i<5; i++) {
            defaultAdventure.createNewStoryPiece();
        }

        StoryPiece fixedSP1 = defaultAdventure.getStoryPieces().get(3);
        StoryPiece fixedSP2 = defaultAdventure.getStoryPieces().get(4);
        int originalOrder1 = fixedSP1.getOrder();
        int originalOrder2 = fixedSP2.getOrder();
        fixedSP1.setFixed(true);
        fixedSP2.setFixed(true);

        defaultAdventure.shuffleStoryPieces();

        assertEquals(originalOrder1, fixedSP1.getOrder(),
                String.format("The order of a fixed StoryPiece has been changed, %d -> %d.", originalOrder1, fixedSP1.getOrder()));
        assertEquals(originalOrder2, fixedSP2.getOrder(),
                String.format("The order of a fixed StoryPiece has been changed, %d -> %d.", originalOrder2, fixedSP2.getOrder()));

    }

    @Test
    public void ShuffleStoryPieceOrder_ShouldNotShuffleStoryPieces_GivenThereAreZeroShuffableStoryPieces() {
        defaultStoryPiece.setFixed(true);
        ArrayList<StoryPiece> storyPiecesOriginalOrder = new ArrayList<>(defaultAdventure.getStoryPieces());

        defaultAdventure.shuffleStoryPieces();
        assertTrue(storyPiecesOriginalOrder.equals(defaultAdventure.getStoryPieces()),
                "The order of StoryPieces was changed although there are zero shuffable StoryPieces.");
    }

}
