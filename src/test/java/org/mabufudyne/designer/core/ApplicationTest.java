package org.mabufudyne.designer.core;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private static Application app;

    @BeforeAll
    public static void obtainApplicationInstance() {
        app = Application.getApp();
    }

    @Test
    public void Initialize_ShouldCreateAdventureAndPopulateCurrentStateWithTheAdventure() {
        app.initialize();

        assertNotNull(app.getCurrentState(),
                "Application currentMemento should be populated after initialization");
        assertEquals(app.getCurrentState().getAdventure(), Adventure.getActiveAdventure(),
                "Application currentMemento does not contain the Adventure created on initialization");
    }

    @Test
    public void SaveState_ShouldMoveCurrentStateToUndoStackAndCreateNewCurrentMemento() {

        app.initialize();
        Memento originalState = app.getCurrentState();

        // Modify Adventure, the new Adventure state should be saved
        Adventure.getActiveAdventure().createNewStoryPiece();

        assertSame(originalState, app.getUndoList().peek(),
                "Former current Memento was not moved to the undo stack after save state operation");
        assertNotSame(originalState, app.getCurrentState(),
                "New current state should not be equal to the former state");
    }

    @Test
    public void Undo_ShouldMoveCurrentStateToRedoStackAndMakeTheTopOfUndoStackTheNewCurrentState_GivenUndoStackIsNotEmpty() {
        // Set up - we will have a current state, 1 state in undo stack and 0 states in redo stack
        app.initialize();
        Adventure.getActiveAdventure().createNewStoryPiece();
        app.saveState();

        Memento currentState = app.getCurrentState();
        Memento undoState = app.getUndoList().peek();

        app.undo();

        assertEquals(currentState, app.getRedoList().peek(),
                "Former current state was not moved to the redo stack after undo operation");
        assertEquals(undoState, app.getCurrentState(),
                "First undo state was not popped and made the new current state after undo operation");

    }

    @Test
    public void Redo_ShouldMoveCurrentStateToUndoStackAndMakeTheTopOfRedoStackTheNewCurentState_GivenRedoStackIsNotEmpty() {
        // Set up - we will have a current state, 0 states in undo stack and 1 state in redo stack
        app.initialize();
        Adventure.getActiveAdventure().createNewStoryPiece("New SP");
        app.saveState();
        app.undo();

        Memento currentState = app.getCurrentState();
        Memento redoState = app.getRedoList().peek();

        app.redo();

        assertEquals(currentState, app.getUndoList().peek(),
                "Former current state was not moved to the undo stack after redo operation");
        assertEquals(redoState, app.getCurrentState(),
                "First redo state was not popped and made the new current state after redo operation");

    }

    @Test
    public void PerformAfterTaskActions_ShouldSaveTheCurrentStateWheneverItIsCalled() {

        app.initialize();
        Memento initialState = app.getCurrentState();

        app.performAfterTaskActions();

        assertEquals(initialState, app.getUndoList().peek(),
                "The method did not move the old current state to the undo stack");
        // Compare by reference, not by contents
        assertNotSame(initialState, app.getCurrentState(),
                "The method did not create a new current state");
    }

}
