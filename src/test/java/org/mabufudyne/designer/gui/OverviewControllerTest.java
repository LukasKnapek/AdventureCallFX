package org.mabufudyne.designer.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mabufudyne.designer.core.Adventure;
import org.mabufudyne.designer.core.Application;
import org.mabufudyne.designer.core.StoryPiece;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OverviewControllerTest {

    private OverviewController controller = new OverviewController();

    private Application app;
    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    @BeforeEach
    void createDefaultStoryPiece() {
        app = new Application(new Properties());
        defaultStoryPiece = new StoryPiece();
        defaultAdventure = new Adventure(app, defaultStoryPiece);
        controller.setApp(app);
    }

    @Test
    public void onAddStoryPieceClick_ShouldAddNewStoryPiece_WhenTheButtonIsClicked() {
        controller.onAddStoryPieceClick();
        assertEquals(2, app.getActiveAdventure().getStoryPieces().size(),
                "The handler should have added a new StoryPiece to the current Adventure");
    }
}
