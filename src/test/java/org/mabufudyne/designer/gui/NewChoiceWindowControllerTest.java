package org.mabufudyne.designer.gui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mabufudyne.designer.core.Adventure;
import org.mabufudyne.designer.core.Application;
import org.mabufudyne.designer.core.StoryPiece;

import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewChoiceWindowControllerTest {

    private Application app;
    private NewChoiceWindowController controller = new NewChoiceWindowController();
    private StoryPiece defaultStoryPiece;
    private Adventure defaultAdventure;

    private TableView<StoryPiece> storyPiecesTable;
    private TableColumn<StoryPiece, Integer> orderColumn;
    private TableColumn<StoryPiece, String> titleColumn;
    private TextField textChoiceDescription;
    private Button btnAddNewChoice;
    private Button btnCancel;

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
        // Model
        app = new Application(new Properties());
        defaultStoryPiece = new StoryPiece();
        defaultAdventure = new Adventure(app, defaultStoryPiece);

        // Controls
        storyPiecesTable = new TableView<>();
        orderColumn = new TableColumn<>();
        titleColumn = new TableColumn<>();
        textChoiceDescription = new TextField();
        btnAddNewChoice = new Button();
        btnCancel = new Button();

        // Controller
        controller = new NewChoiceWindowController(storyPiecesTable,
                                                   orderColumn,
                                                   titleColumn,
                                                   textChoiceDescription,
                                                   btnAddNewChoice,
                                                   btnCancel);
        controller.setApp(app);
    }

    @Test
    void onCancelClick_ShouldCloseTheWindow() {
        controller.initialize();
        controller.setCurrentStoryPiece(defaultStoryPiece);
        controller.loadData(defaultAdventure.getStoryPieces());

        Platform.runLater(() -> {
            Stage testStage = new Stage();
            controller.setStage(testStage);
            controller.onCancelClick();
            assertFalse(testStage.isShowing());
        });
    }

    @Test
    void onAddNewChoiceClick_ShouldAddSelectedStoryPieceAsChoiceToCurrentlySelectedStoryPiece() {
        StoryPiece choiceSP = new StoryPiece();
        defaultAdventure.addStoryPiece(choiceSP);

        controller.initialize();
        controller.setCurrentStoryPiece(defaultStoryPiece);
        controller.loadData(defaultAdventure.getStoryPieces());

        storyPiecesTable.getSelectionModel().select(choiceSP);
        controller.onAddNewChoiceClick();

        assertEquals(defaultStoryPiece.getChoices().size(), 1);
        assertEquals(defaultStoryPiece.getChoices().get(0).getStoryPiece(), choiceSP);
    }
}
