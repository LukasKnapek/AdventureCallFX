package org.mabufudyne.designer.core;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    private static Application app;
    private static Adventure initialAdventure;

    @BeforeEach
    void obtainApplicationInstance() {
        app = new Application(new Properties());
        initialAdventure = new Adventure(app, new StoryPiece());
    }

    @Test
    void SaveState_ShouldMoveCurrentStateToUndoStackAndCreateNewCurrentMemento() {
        Memento originalState = app.getCurrentState();

        // Modify Adventure, the new Adventure state should be saved
        StoryPiece sp = new StoryPiece();
        initialAdventure.addStoryPiece(sp);

        assertSame(originalState, app.getUndoList().peek(),
                "Former current Memento was not moved to the undo stack after save state operation");
        assertNotSame(originalState, app.getCurrentState(),
                "New current state should not be equal to the former state");
    }


    @Test
    void Undo_ShouldReassignActiveAdventureAfterLoadingTheOldState() {
        // Create a new state, the old one will be moved to Undo stack
        initialAdventure.addStoryPiece(new StoryPiece());

        // Get the Adventure in the old state
        Adventure undoAdventure = app.getUndoList().get(0).getAdventure();
        app.undo();

        // After undo, the Adventure in the old state should be now the active one
        assertSame(undoAdventure, app.getActiveAdventure(),
                "Adventure from last Undo state was is not linked in the current Application");
        assertSame(undoAdventure.getParentApp(), app,
                "Adventure from last Undo state does not have the current Application linked");
    }


    @Test
    void Undo_ShouldMoveCurrentStateToRedoStackAndMakeTheTopOfUndoStackTheNewCurrentState_GivenUndoStackIsNotEmpty() {
        // Set up - we will have a current state, 1 state in undo stack and 0 states in redo stack
        StoryPiece sp = new StoryPiece();
        initialAdventure.addStoryPiece(sp);
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
    void Redo_ShouldReassignActiveAdventureAfterLoadingTheOldState() {
        // Create a new state, Undo stack size is now 1
        initialAdventure.addStoryPiece(new StoryPiece());

        // Undo, Redo stack size is now 1
        app.undo();

        // Redo, the adventure from the old state in Redo stack should be now linked with the application
        Adventure redoAdventure = app.getRedoList().get(0).getAdventure();
        app.redo();

        assertSame(redoAdventure, app.getActiveAdventure(),
                "Adventure from last Redo state was is not linked in the current Application");
        assertSame(redoAdventure.getParentApp(), app,
                "Adventure from last Undo state does not have the current Application linked");
    }

    @Test
    void Redo_ShouldMoveCurrentStateToUndoStackAndMakeTheTopOfRedoStackTheNewCurentState_GivenRedoStackIsNotEmpty() {
        // Set up - we will have a current state, 0 states in undo stack and 1 state in redo stack
        StoryPiece sp = new StoryPiece();
        initialAdventure.addStoryPiece(sp);
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
    void PerformAfterTaskActions_ShouldSaveTheCurrentStateWheneverItIsCalled() {
        Memento initialState = app.getCurrentState();

        app.performAfterTaskActions();

        assertEquals(initialState, app.getUndoList().peek(),
                "The method did not move the old current state to the undo stack");
        // Compare by reference, not by contents
        assertNotSame(initialState, app.getCurrentState(),
                "The method did not create a new current state");
    }

}
