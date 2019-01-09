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
        app = new Application(new Properties());
        defaultStoryPiece = new StoryPiece();
        defaultAdventure = new Adventure(app, defaultStoryPiece);

        orderSpinner = new Spinner<>();
        titleField = new TextField();
        storyArea = new TextArea();
        controller = new StoryPieceViewController(orderSpinner, titleField, storyArea);
        controller.setApp(app);
    }

    @Test
    public void onStoryPiecesTableNewSelection_ShouldPopulateTheViewControlsWithTheCurrentlySelectedStoryPieceValues() {
        // Give the SP other than default values
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

        defaultStoryPiece.setTitle("Old title");
        defaultStoryPiece.setStory("Old story");
        secondSP.setTitle("New and shiny story");
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
        // Set up OverviewController with populated table so the method can check which StoryPiece is currently selected
        controller.mainController = new MainWindowController();
        OverviewController oc = new OverviewController();
        oc.storyPiecesTable = new TableView<>(defaultAdventure.getStoryPieces());
        controller.mainController.overviewController = oc;

        defaultAdventure.addStoryPiece(new StoryPiece());
        // Select SP with order one and manually call the listener to populate the View controls with the SP details
        oc.storyPiecesTable.getSelectionModel().select(defaultStoryPiece);
        controller.onStoryPiecesTableNewSelection(defaultStoryPiece);
        // Set its new order to 2 using the spinner
        orderSpinner.getValueFactory().setValue(2);

        // The order of the SP should be changed to 2 now
        controller.onOrderSpinnerFocusLost(false);
        assertEquals(2, defaultStoryPiece.getOrder());
    }
}
