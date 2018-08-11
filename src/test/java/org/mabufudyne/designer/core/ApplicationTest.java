package org.mabufudyne.designer.core;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    @Test
    public void Constructor_ShouldBeInaccessible_GivenApplicationIsOnlyUsedStatically() throws Exception {
        Constructor[] appConstructors = Application.class.getDeclaredConstructors();
        assertEquals(1, appConstructors.length,
                "Application class should only have one constructor.");
        Constructor appConstructor = appConstructors[0];
        assertFalse(appConstructor.isAccessible(),
                "Application constructor should be inaccessible - private");

        appConstructor.setAccessible(true);
        // Instantiating just to get that sweet, sweet test coverage
        assertEquals(Application.class, appConstructor.newInstance().getClass());
    }

    @Test
    public void Initialize_ShouldCreateAdventureAndPopulateCurrentStateWithTheAdventure() {

        Application.initialize();

        assertNotNull(Application.getCurrentState(),
                "Application currentMemento should be populated after initialization");
        assertEquals(Application.getCurrentState().getAdventure(), Adventure.getActiveAdventure(),
                "Application currentMemento does not contain the Adventure created on initialization");


    }

    @Test
    public void SaveState_ShouldMoveCurrentStateToUndoStackAndCreateNewCurrentMemento() {

        Application.initialize();
        Memento originalState = Application.getCurrentState();

        // Modify Adventure and save state, the new state should be different from the old one
        Adventure.getActiveAdventure().createNewStoryPiece();
        Application.saveState();


        assertEquals(originalState, Application.getUndoStack().peek(),
                "Former current Memento was not moved to the undo stack after save state operation");
        assertNotEquals(originalState, Application.getCurrentState(),
                "New current state should not be equal to the former state");
    }

    @Test
    public void Undo_ShouldMoveCurrentStateToRedoStackAndMakeTheTopOfUndoStackTheNewCurrentState_GivenUndoStackIsNotEmpty() {
        // Set up - we will have a current state, 1 state in undo stack and 0 states in redo stack
        Application.initialize();
        Adventure.getActiveAdventure().createNewStoryPiece();
        Application.saveState();

        Memento currentState = Application.getCurrentState();
        Memento undoState = Application.getUndoStack().peek();

        Application.undo();

        assertEquals(currentState, Application.getRedoStack().peek(),
                "Former current state was not moved to the redo stack after undo operation");
        assertEquals(undoState, Application.getCurrentState(),
                "First undo state was not popped and made the new current state after undo operation");

    }

    @Test
    public void Redo_ShouldMoveCurrentStateToUndoStackAndMakeTheTopOfRedoStackTheNewCurentState_GivenRedoStackIsNotEmpty() {
        // Set up - we will have a current state, 0 states in undo stack and 1 state in redo stack
        Application.initialize();
        Adventure.getActiveAdventure().createNewStoryPiece("New SP");
        Application.saveState();
        Application.undo();

        Memento currentState = Application.getCurrentState();
        Memento redoState = Application.getRedoStack().peek();

        Application.redo();

        assertEquals(currentState, Application.getUndoStack().peek(),
                "Former current state was not moved to the undo stack after redo operation");
        assertEquals(redoState, Application.getCurrentState(),
                "First redo state was not popped and made the new current state after redo operation");

    }

}
