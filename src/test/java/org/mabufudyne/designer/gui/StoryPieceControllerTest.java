package org.mabufudyne.designer.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Spinner;
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

public class StoryPieceControllerTest {

    private Application app;
    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;

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
    }

    @Test
    public void onStoryPieceViewTableNewSelection_ShouldPopulateTheViewControlsWithTheCurrentlySelectedStoryPieceValues() {
        // Give the SP other than default values
        defaultStoryPiece.setTitle("Beginning!");
        defaultStoryPiece.setStory("Once upon a time...");

        // Populate controller with controls and model
        Spinner<Integer> orderSpinner = new Spinner<>();
        TextField titleField = new TextField();
        TextArea storyArea = new TextArea();
        StoryPieceViewController controller = new StoryPieceViewController(orderSpinner, titleField, storyArea);
        controller.setApp(app);

        controller.onStoryPiecesTableNewSelection(defaultStoryPiece);

        assertEquals(defaultStoryPiece.getOrder(), (int) orderSpinner.getValue(),
                "The handler did not populate the order spinner with the order of the StoryPiece.");
        assertEquals(defaultStoryPiece.getTitle(), titleField.getText(),
                "The handler did not populate the title field with the title of the StoryPiece.");
        assertEquals(defaultStoryPiece.getStory(), storyArea.getText(),
                "The handler did not populate the story field with the story of the StoryPiece.");
    }

    @Test
    public void onStoryPieceViewTableNewSelection_ShouldReplacePreviouslySelectedStoryPieceValueWithNewlySelectedStoryPieceValues() {
        // TODO: Make sure View field values are replacet and not just concatenated
    }
}
