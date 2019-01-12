package org.mabufudyne.designer.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mabufudyne.designer.core.Adventure;
import org.mabufudyne.designer.core.Application;
import org.mabufudyne.designer.core.StoryPiece;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoryPieceViewControllerTest {

    private Application app;
    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

    private StoryPieceViewController controller;
    private OverviewController oc;

    private Spinner<Integer> orderSpinner;
    private TextField titleField;
    private TextArea storyArea;

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

        // Controller and its controls
        orderSpinner = new Spinner<>();
        titleField = new TextField();
        storyArea = new TextArea();
        controller = new StoryPieceViewController(orderSpinner, titleField, storyArea);
        controller.setApp(app);

        // Overview Controller and its populated TableView, which is accessed often in the class under test
        oc = new OverviewController();
        oc.storyPiecesTable = new TableView<>(defaultAdventure.getStoryPieces());
        controller.mainController = new MainWindowController();
        controller.mainController.overviewController = oc;
    }

    @Test
    public void onStoryPiecesTableNewSelection_ShouldPopulateTheViewControlsWithTheCurrentlySelectedStoryPieceValues() {
        defaultStoryPiece.setTitle("Beginning!");
        defaultStoryPiece.setStory("Once upon a time...");

        controller.onStoryPiecesTableNewSelection(defaultStoryPiece);

        assertEquals(defaultStoryPiece.getOrder(), (int) orderSpinner.getValue(),
                "The handler did not populate the order spinner with the order of the StoryPiece.");
        assertEquals(defaultStoryPiece.getTitle(), titleField.getText(),
                "The handler did not populate the title field with the title of the StoryPiece.");
        assertEquals(defaultStoryPiece.getStory(), storyArea.getText(),
                "The handler did not populate the story field with the story of the StoryPiece.");
    }

    @Test
    public void onStoryPiecesTableNewSelection_ShouldReplacePreviouslySelectedStoryPieceValueWithNewlySelectedStoryPieceValues() {
        StoryPiece secondSP = new StoryPiece();
        defaultAdventure.addStoryPiece(secondSP);
        secondSP.setTitle("New and shiny title");
        secondSP.setStory("New and better story");

        controller.onStoryPiecesTableNewSelection(defaultStoryPiece);
        controller.onStoryPiecesTableNewSelection(secondSP);

        assertEquals(secondSP.getOrder(), (int) orderSpinner.getValue(),
                "The handler did not correctly populate the order spinner on second SP selection.");
        assertEquals(secondSP.getTitle(), titleField.getText(),
                "The handler did not correctly populate the title field on second SP selection.");
        assertEquals(secondSP.getStory(), storyArea.getText(),
                "The handler did not correctly populate the story field on second SP selection.");
    }

    @Test
    public void onOrderSpinnerFocusLost_ShouldChangeTheCurrentlySelectedStoryPieceOrder_GivenItHasBeenChangedInTheSpinner() {
        defaultAdventure.addStoryPiece(new StoryPiece());

        oc.storyPiecesTable.getSelectionModel().select(defaultStoryPiece);
        controller.onStoryPiecesTableNewSelection(defaultStoryPiece);

        orderSpinner.getValueFactory().setValue(2);
        controller.onOrderSpinnerFocusLost();
        assertEquals(2, defaultStoryPiece.getOrder());
    }

    @Test
    public void onTitleFieldFocusLost_ShouldSaveTheNewStoryPieceTitle() {
        String newTitle = "Changed title";

        oc.storyPiecesTable.getSelectionModel().select(defaultStoryPiece);
        controller.onStoryPiecesTableNewSelection(defaultStoryPiece);

        titleField.setText(newTitle);
        controller.onTitleFieldFocusLost();
        assertEquals(newTitle, defaultStoryPiece.getTitle());
    }
}
