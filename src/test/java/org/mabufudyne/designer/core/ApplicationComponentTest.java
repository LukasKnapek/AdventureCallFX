package org.mabufudyne.designer.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationComponentTest {

    @BeforeEach
    public void SetUpCleanApplicationAndAdventure() {
        // Clean Adventure
        Application.getApp().initialize();
        // Clean Application state history
        Application.getApp().resetStateHistory();
    }

    @Test
    public void Application_ShouldSetTheCurrentStateToTheSameStateAfterAnUndoAndRedo_GivenUndoRedoAreAvailable() {

        // Initial current state, empty undo/redo stacks
        Application.getApp().initialize();

        // One state in undo stack
        Adventure.getActiveAdventure().createNewStoryPiece("StoryPiece 2");

        // Two states in undo stack
        Adventure.getActiveAdventure().getStoryPieces().get(0).setFixed(true);

        // One state in undo stack, one state in redo stack
        Application.getApp().undo();

        Memento stateBeforeUndoRedo = Application.getApp().getCurrentState();

        Application.getApp().undo();
        Application.getApp().redo();

        assertSame(stateBeforeUndoRedo, Application.getApp().getCurrentState(),
                "The application state is not the same after undo followed by redo");

    }

    @Test
    public void Application_CurrentStateShouldContainTheCorrectAdventureAfterASequenceOfStateChanges() {
        // Initial state with one SP
        Application.getApp().initialize();

        Adventure initialAdventure = Adventure.getActiveAdventure();

        // 2 default SPs
        initialAdventure.createNewStoryPiece();

        StoryPiece sp1 = initialAdventure.getStoryPieces().get(0);
        StoryPiece sp2 = initialAdventure.getStoryPieces().get(1);

        // 2 SPs, sp1 title is "Beginning"
        sp1.setTitle("Beginning");

        // 2 SPs, sp1 title is "Beginning", sp2 is fixed
        sp2.setFixed(true);

        // 2 SPs, sp1 title is "Beginning" and story is "To be continued", sp2 is fixed
        sp1.setStory("To be continued");

        // 2 SPs, sp1 title is "Beginning", sp2 is fixed
        Application.getApp().undo();

        // 2 SPs, sp1 title is "Beginning"
        Application.getApp().undo();

        // 2 SPs, sp1 title is "Beginning", sp2 color is black
        sp2.setColor("FFFFFF");

        // 2 SPs, sp1 title is "Beginning", sp2 color is black and is fixed
        sp2.setFixed(true);

        // 2 SPs, sp1 title is "Beginning", sp2 color is black
        Application.getApp().undo();

        // 2 SPs, sp1 title is "Beginning", sp2 color is black and is fixed
        Application.getApp().redo();

        Adventure modifiedAdventure = Application.getApp().getCurrentState().getAdventure();
        // Check: 2 SPs
        assertEquals(2, modifiedAdventure.getStoryPieces().size());
        // Check: sp1 title is "Beginning"
        assertEquals("Beginning", modifiedAdventure.getStoryPieces().get(0).getTitle());
        // Check: sp2 color is black
        assertEquals("FFFFFF", modifiedAdventure.getStoryPieces().get(1).getColor());
        // Check: sp2 is fixed
        assertTrue(modifiedAdventure.getStoryPieces().get(1).isFixed());
    }
}
