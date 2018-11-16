package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Random;

class AdventureTest {

    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    @BeforeEach
    void createDefaultObjects() {
        defaultStoryPiece = new StoryPiece();
        defaultAdventure = new Adventure(defaultStoryPiece);
    }

    @Test
    void StoryPieces_ShouldBeAnObservableList() {
        assertTrue(defaultAdventure.getStoryPieces() instanceof ObservableList);
    }

    @Test
    void Constructor_ShouldCreateAnAdventureWithName_GivenTheName() {
        StoryPiece sp = new StoryPiece();
        Adventure testAdv = new Adventure(sp, "My First Adventure");
        assertEquals("My First Adventure", testAdv.getName(),
                "Name of the Adventure should be equal to the name that was passed to the constructor");
    }

    @Test
    void Constructor_ShouldCreateAnAdventureWithDefaultName_GivenNoName() {
        assertEquals(Adventure.getDefaultName(), defaultAdventure.getName(),
                "The Adventure name should be the default name");
    }

    @Test
    void Constructor_ShouldCreateAnAdventureWithOneStoryPiece() {
        assertEquals(1, defaultAdventure.getStoryPieces().size(),
                "The Adventure has to contain at least one StoryPiece at all times," +
                        " including immediately after creation");
    }

    @Test
    void RemoveStoryPiece_ShouldRemoveStoryPieceFromAdventure_GivenTheStoryPiece() {
        StoryPiece newSP = new StoryPiece();
        defaultAdventure.addStoryPiece(newSP);

        defaultAdventure.removeStoryPiece(newSP);
        assertFalse(defaultAdventure.getStoryPieces().contains(newSP),
                "The Adventure should not contain the StoryPiece that was removed from it");
    }

    @Test
    void RemoveStoryPiece_ShouldRemoveStoryPiecesFromAdventure_GivenTheStoryPieces() {
        StoryPiece[] storyPieces = new StoryPiece[5];

        for (int i=0; i<5; i++) {
            storyPieces[i] = new StoryPiece();
            defaultAdventure.addStoryPiece(storyPieces[i]);
        }

        for (StoryPiece sp : storyPieces) {
            defaultAdventure.removeStoryPiece(sp);
            assertFalse(defaultAdventure.getStoryPieces().contains(sp),
                    "The Adventure should not contain one of the StoryPieces that were removed from it");
        }
    }

    @Test
    void RemoveStoryPiece_ShouldNotRemoveStoryPieceFromAdventure_GivenItIsTheLastOne() {
        StoryPiece sp = defaultAdventure.getStoryPieces().get(0);
        defaultAdventure.removeStoryPiece(sp);
        assertTrue(defaultAdventure.getStoryPieces().contains(sp),
                "The Adventure should not have removed the last StoryPiece it has");
    }

    @Test
    void RemoveStoryPiece_ShouldRemoveStoryPiecesFromAdventureExceptForTheLastOne_GivenTheStoryPieces() {
        StoryPiece[] storyPieces = new StoryPiece[5];
        storyPieces[0] = defaultAdventure.getStoryPieces().get(0);

        for (int i=1; i<5; i++) {
            storyPieces[i] = new StoryPiece();
            defaultAdventure.addStoryPiece(storyPieces[i]);
        }

        for (StoryPiece sp : storyPieces) {
            defaultAdventure.removeStoryPiece(sp);
        }

        assertTrue(defaultAdventure.getStoryPieces().size() > 0,
                "The Adventure should not have removed its last StoryPiece");
    }

    @Test
    void RemoveStoryPiece_ShouldOnlyRemoveSpecificStoryPiecesFromAdventure_GivenTheStoryPieces() {
        // Create 10 StoryPieces inside an Adventure, remove random number of them, see if the rest remains
        ArrayList<StoryPiece> spList = new ArrayList<>();

        for (int i=0; i<10; i++) {
            spList.add(new StoryPiece());
            defaultAdventure.addStoryPiece(spList.get(i));
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
    void RemoveStoryPiece_ShouldPerformCommonAfterTaskOperationsAfterRemovingTheStoryPiece() {
        StoryPiece newSP = new StoryPiece();
        defaultAdventure.addStoryPiece(newSP);

        Application.getApp().reset();
        defaultAdventure.removeStoryPiece(newSP);

        WereAfterTasksPerformedCorrectly(1);

    }

    @Test
    void SwitchStoryPieceOrder_ShouldSwitchTheOrderOfTheStoryPieces_GivenTheFirstStoryPieceAndItsNewOrder() {

        for (int i=0; i<5; i++) {
            StoryPiece sp = new StoryPiece();
            defaultAdventure.addStoryPiece(sp);
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
    void SwitchStoryPieceOrder_ShouldNotSwitchTheOrderOfTheStoryPieces_GivenAStoryPieceAndItsCurrentOrderAsTheNewOrder() {

        StoryPiece newSP = new StoryPiece();
        defaultAdventure.addStoryPiece(newSP);
        int currentOrder = newSP.getOrder();

        defaultAdventure.switchStoryPieceOrder(newSP, currentOrder);
        assertEquals(currentOrder, newSP.getOrder(),
                "The StoryPiece order was changed although its new order argument was its current order.");
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, 100 })
    void SwitchStoryPieceOrder_ShouldThrowAnException_GivenANewOrderThatIsOutsideTheRange1ToTheNumberOfExistingStoryPieces(int order) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            defaultAdventure.switchStoryPieceOrder(defaultStoryPiece, order);
        });
        assertEquals("The requested new order is out of range (1-Number of existing StoryPieces).", ex.getMessage(),
                "The exception did not have the correct message.");
    }

    @Test
    void SwitchStoryPieceOrder_ShouldPerformCommonAfterTaskOperationsAfterSwitchingTheOrder() {
        StoryPiece sp = new StoryPiece();
        defaultAdventure.addStoryPiece(sp);

        Application.getApp().reset();
        defaultAdventure.switchStoryPieceOrder(defaultStoryPiece, 2);

        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    void ShuffleStoryPieceOrder_ShouldRandomizeTheOrderOfStoryPiecesInAnAdventure() {

        for (int i=0; i<5; i++) {
            StoryPiece sp = new StoryPiece();
            defaultAdventure.addStoryPiece(sp);
        }

        ArrayList<StoryPiece> storyPiecesOriginalOrder = new ArrayList<>(defaultAdventure.getStoryPieces());
        defaultAdventure.shuffleStoryPieces();

        assertFalse(storyPiecesOriginalOrder.equals(defaultAdventure.getStoryPieces()),
                "The order of the StoryPieces has not changed after shuffle.");
    }

    @Test
    void ShuffleStoryPieceOrder_ShouldNotResultInTheSameOrderAsBefore_GivenThereAreAtLeastTwoShuffableStoryPieces() {

        // Create a new StoryPiece so we have 2 in total
        StoryPiece sp = new StoryPiece();
        defaultAdventure.addStoryPiece(sp);

        // 50% chance of the correct order after shuffle even if the method does not work correctly, so repeat 10 times
        // to reduce the probability that the order gets shuffled correctly each time by chance to ~0.09%
        for (int i=0; i<10; i++) {
            ArrayList<StoryPiece> storyPiecesOriginalOrder = new ArrayList<>(defaultAdventure.getStoryPieces());
            defaultAdventure.shuffleStoryPieces();
            assertNotEquals(storyPiecesOriginalOrder, defaultAdventure.getStoryPieces(),
                    "The order of the StoryPieces has not changed after shuffle even though there are two" +
                    "shuffable StoryPieces.");
        }
    }

    @Test
    void ShuffleStoryPieceOrder_ShouldNotShuffleStoryPiecesWithFixedStatus() {
        for (int i=0; i<5; i++) {
            StoryPiece sp = new StoryPiece();
            defaultAdventure.addStoryPiece(sp);
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
    void ShuffleStoryPieceOrder_ShouldNotShuffleStoryPieces_GivenThereAreZeroShuffableStoryPieces() {
        defaultStoryPiece.setFixed(true);
        ArrayList<StoryPiece> storyPiecesOriginalOrder = new ArrayList<>(defaultAdventure.getStoryPieces());

        defaultAdventure.shuffleStoryPieces();
        assertEquals(storyPiecesOriginalOrder, defaultAdventure.getStoryPieces(),
                "The order of StoryPieces was changed although there are zero shuffable StoryPieces.");
    }

    @Test
    void ShuffleStoryPieceOrder_ShouldPerformCommonAfterTaskOperationsOnceAfterShufflingTheOrder() {
        StoryPiece newSP = new StoryPiece();
        defaultAdventure.addStoryPiece(newSP);

        Application.getApp().reset();
        defaultAdventure.shuffleStoryPieces();

        WereAfterTasksPerformedCorrectly(1);
    }

    @Test
    void EqualsHashCode_ShouldBeReflexive() {
        Adventure adv = defaultAdventure;

        assertEquals(adv, adv,
                "An Adventure is not considered equal to itself.");
        assertEquals(adv.hashCode(), adv.hashCode(),
                "An Adventure hashcode is not reflexive.");
    }

    @Test
    void EqualsHashCode_ShouldBeSymmetric() {
        Adventure adv1 = defaultAdventure;
        Adventure adv2 = new Adventure(defaultStoryPiece);

        assertEquals(adv1, adv2,
                "Two same Adventures are not considered equal.");
        assertEquals(adv1.hashCode(), adv2.hashCode(),
                "Two same Adventures have different hash codes.");
    }

    @Test
    void EqualsHashCode_ShouldBeTransitive() {
        Adventure adv1 = defaultAdventure;
        Adventure adv2 = new Adventure(defaultStoryPiece);
        Adventure adv3 = new Adventure(defaultStoryPiece);

        assertTrue(adv1.equals(adv2) &&
                        adv2.equals(adv3) &&
                        adv1.equals(adv3),
                "The transitive property does not apply to three equal Adventures.");
        assertTrue(adv1.hashCode() == adv2.hashCode() &&
                        adv2.hashCode() == adv3.hashCode() &&
                        adv1.hashCode() == adv3.hashCode(),
                "The transitive property doest not apply to hash codes of three equal Adventures.");
    }

    @Test
    void SetName_ShouldSetTheAdventureName_GivenTheName() {
        String newName = "Unicorn land!";
        defaultAdventure.setName(newName);

        assertEquals(newName, defaultAdventure.getName(),
                "Adventure does not have the name that was assigned to it.");
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
