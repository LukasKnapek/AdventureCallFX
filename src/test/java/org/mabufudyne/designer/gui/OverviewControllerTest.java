package org.mabufudyne.designer.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mabufudyne.designer.core.Adventure;
import org.mabufudyne.designer.core.Application;
import org.mabufudyne.designer.core.StoryPiece;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;


public class OverviewControllerTest {

    private OverviewController controller = new OverviewController();

    private Application app;
    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;
    private TableView<StoryPiece> defaultTable;

    /**
     * Creates a new JFXPanel, which launches the FX environment/toolkit.
     * We need this in order to instantiate and work with JFX controls in tests
     */
    @BeforeAll
    public static void setUpFX() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @BeforeEach
    void createDefaultObjects() {
        app = new Application(new Properties());
        defaultStoryPiece = new StoryPiece();
        defaultAdventure = new Adventure(app, defaultStoryPiece);
        defaultTable = new TableView<>();
        defaultTable.setItems(defaultAdventure.getStoryPieces());

        controller.setApp(app);
        controller.setStoryPiecesTable(defaultTable);
    }

    @Test
    public void onAddStoryPieceClick_ShouldAddNewStoryPiece_WhenTheButtonIsClicked() {
        // Add StoryPiece, starting with the default one, we should have two afterwards
        controller.onAddStoryPieceClick();
        assertEquals(2, app.getActiveAdventure().getStoryPieces().size(),
                "The handler should have added a new StoryPiece to the current Adventure");
    }

    @Test
    public void onRemoveStoryPieceClick_ShouldRemoveASingleStoryPiece_GivenThereIsMoreThanOneStoryPiece() {
        // Create SP, we have two now
        defaultAdventure.addStoryPiece(new StoryPiece());

        // Make sure an item is selected in our default table
        defaultTable.getSelectionModel().select(0);

        controller.onRemoveStoryPieceClick();
        assertEquals(1, app.getActiveAdventure().getStoryPieces().size(),
                "The handler should have removed a StoryPiece from the current Adventure");
    }

    @Test
    public void onRemoveStoryPieceClick_ShouldOnlyRemoveTheCurrentlySelectedStoryPiece_GivenThereAreMoreThanOneStoryPiece() {
        // Create a few SPs, only this one is expected to be removed
        StoryPiece SPtoBeRemoved = new StoryPiece();
        defaultAdventure.addStoryPiece(new StoryPiece());
        defaultAdventure.addStoryPiece(SPtoBeRemoved);
        defaultAdventure.addStoryPiece(new StoryPiece());

        defaultTable.getSelectionModel().select(SPtoBeRemoved);
        controller.onRemoveStoryPieceClick();
        assertFalse(app.getActiveAdventure().getStoryPieces().contains(SPtoBeRemoved),
                "The handler did not remove the currently selected StoryPiece");
    }
}
