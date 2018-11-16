package org.mabufudyne.designer.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    private static Application app;
    private static Adventure initialAdventure;

    @BeforeAll
    static void obtainApplicationInstance() {
        app = Application.getApp();
    }

    @BeforeEach
    void resetApplicationState() {
        Application.getApp().reset();
    }

    @Test
    void Initialize_ShouldCreateAppPropertiesUsingPropertiesClass() throws IOException {
        app.initialize();

        assertTrue(app.getProperties() instanceof Properties);
    }

    @Test
    void Initialize_ShouldCreateDefaultProperties() throws IOException {
        app.reset();
        app.initialize();

        try {
            FileInputStream in = new FileInputStream("config.properties");
            Properties defaultProps = new Properties();
            defaultProps.load(in);

            assertEquals(app.getProperties(), defaultProps);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void Initialize_ShouldThrowException_GivenDefaultPropertiesFileDoesNotExist() {
        app.setPropertiesPath("non-existent path");

        assertThrows(IOException.class, () -> {
            app.initialize();
        }, "Initialize was given a fake settings path, but it didn't throw an exception");
    }

    @Test
    void Initialize_ShouldCreateAdventureAndPopulateCurrentStateWithTheAdventure() throws IOException {
        app.reset();
        app.initialize();

        assertNotNull(app.getCurrentState(),
                "Application currentMemento should be populated after initialization");
        assertEquals(app.getCurrentState().getAdventure(), Adventure.getActiveAdventure(),
                "Application currentMemento does not contain the Adventure created on initialization");
    }

    @Test
    void SaveState_ShouldMoveCurrentStateToUndoStackAndCreateNewCurrentMemento() throws IOException {
        app.initialize();
        Memento originalState = app.getCurrentState();

        // Modify Adventure, the new Adventure state should be saved
        StoryPiece sp = new StoryPiece();
        Adventure.getActiveAdventure().addStoryPiece(sp);

        assertSame(originalState, app.getUndoList().peek(),
                "Former current Memento was not moved to the undo stack after save state operation");
        assertNotSame(originalState, app.getCurrentState(),
                "New current state should not be equal to the former state");
    }

    @Test
    void SaveState_ShouldNotBeExecuted_GivenSaveStatePropertyIsFalse() throws IOException {
        Adventure initialAdventure = app.initialize();
        app.getProperties().setProperty("saveStates", "false");

        Memento initialState = app.getCurrentState();
        // Should not create a new state
        StoryPiece sp = new StoryPiece();
        initialAdventure.addStoryPiece(sp);

        assertEquals(initialState, app.getCurrentState());

    }

    @Test
    void Undo_ShouldMoveCurrentStateToRedoStackAndMakeTheTopOfUndoStackTheNewCurrentState_GivenUndoStackIsNotEmpty() throws IOException {
        // Set up - we will have a current state, 1 state in undo stack and 0 states in redo stack
        app.initialize();

        StoryPiece sp = new StoryPiece();
        Adventure.getActiveAdventure().addStoryPiece(sp);
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
    void Redo_ShouldMoveCurrentStateToUndoStackAndMakeTheTopOfRedoStackTheNewCurentState_GivenRedoStackIsNotEmpty() throws IOException {
        // Set up - we will have a current state, 0 states in undo stack and 1 state in redo stack
        app.initialize();

        StoryPiece sp = new StoryPiece();
        Adventure.getActiveAdventure().addStoryPiece(sp);
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
    void PerformAfterTaskActions_ShouldSaveTheCurrentStateWheneverItIsCalled() throws IOException {

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
