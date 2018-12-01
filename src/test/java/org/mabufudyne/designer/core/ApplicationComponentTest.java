package org.mabufudyne.designer.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationComponentTest {

    private Application app;
    private Adventure defaultAdventure;

    @BeforeEach
    void SetUpCleanApplicationAndAdventure() {
        app = new Application(new Properties());
        defaultAdventure = new Adventure(app, new StoryPiece());
    }

    @Test
    void Application_ShouldSetTheCurrentStateToTheSameStateAfterAnUndoAndRedo_GivenUndoAndRedoAreAvailable() {
        // One state in undo stack
        StoryPiece sp = new StoryPiece();
        defaultAdventure.addStoryPiece(sp);

        // Two states in undo stack
        defaultAdventure.getStoryPieces().get(0).setFixed(true);

        // One state in undo stack, one state in redo stack
        app.undo();

        Memento stateBeforeUndoRedo = app.getCurrentState();

        app.undo();
        app.redo();

        assertSame(stateBeforeUndoRedo, app.getCurrentState(),
                "The application state is not the same after undo followed by redo");
    }

    @Test
    void Application_CurrentStateShouldContainTheCorrectAdventureAfterASequenceOfStateChanges() throws IOException {
        // 2 default SPs
        StoryPiece anotherSP = new StoryPiece();
        defaultAdventure.addStoryPiece(anotherSP);

        StoryPiece sp1 = defaultAdventure.getStoryPieces().get(0);
        StoryPiece sp2 = defaultAdventure.getStoryPieces().get(1);

        // 2 SPs, sp1 title is "Beginning"
        sp1.setTitle("Beginning");

        // 2 SPs, sp1 title is "Beginning", sp2 is fixed
        sp2.setFixed(true);

        // 2 SPs, sp1 title is "Beginning" and story is "To be continued", sp2 is fixed
        sp1.setStory("To be continued");

        // 2 SPs, sp1 title is "Beginning", sp2 is fixed
        app.undo();

        // 2 SPs, sp1 title is "Beginning"
        app.undo();

        sp2 = app.getActiveAdventure().getStoryPieces().get(1);

        // 2 SPs, sp1 title is "Beginning", sp2 color is black
        sp2.setColor("FFFFFF");

        // 2 SPs, sp1 title is "Beginning", sp2 color is black and is fixed
        sp2.setFixed(true);

        // 2 SPs, sp1 title is "Beginning", sp2 color is black
        app.undo();

        // 2 SPs, sp1 title is "Beginning", sp2 color is black and is fixed
        app.redo();

        Adventure modifiedAdventure = app.getCurrentState().getAdventure();
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
