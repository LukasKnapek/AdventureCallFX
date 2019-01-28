package org.mabufudyne.designer.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mabufudyne.designer.core.Adventure;
import org.mabufudyne.designer.core.Application;
import org.mabufudyne.designer.core.Choice;
import org.mabufudyne.designer.core.StoryPiece;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChoiceViewControllerTest {

    private ChoiceViewController controller = new ChoiceViewController();

    private Application app;
    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;
    private TableView<Choice> tableChoices;

    /**
     * Creates a new JFXPanel, which launches the FX environment/toolkit.
     * We need this in order to instantiate and work with JFX controls in tests
     */
    @BeforeAll
    public static void setUpFX() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @BeforeEach
    void setUpController() {
        createControllerObjects();
        createAuxiliaryControllersAndTheirObjects();
    }

    void createControllerObjects() {
        app = new Application(new Properties());
        defaultStoryPiece = new StoryPiece();
        defaultAdventure = new Adventure(app, defaultStoryPiece);
        tableChoices = new TableView<>();

        controller.setApp(app);
        controller.setChoicesTable(tableChoices);
    }

    void createAuxiliaryControllersAndTheirObjects() {
        // ChoiceViewController needs to be able to access the (selected) StoryPiece(s) in Overview
        MainWindowController mc = new MainWindowController();
        OverviewController oc = new OverviewController();
        TableView<StoryPiece> spTable = new TableView<>();

        mc.setOverviewController(oc);
        oc.setStoryPiecesTable(spTable);
        spTable.setItems(defaultAdventure.getStoryPieces());
        spTable.getSelectionModel().select(defaultStoryPiece);

        controller.setMainController(mc);
    }

    @Test
    public void setUpControls_ShouldPopulateTheChoicesTable() {
        StoryPiece spTwo = new StoryPiece();
        Choice defaultChoice = new Choice(spTwo, "I am a choice!");
        defaultAdventure.addStoryPiece(spTwo);
        defaultStoryPiece.addChoice(defaultChoice);

        // Default SP has one Choice that should show up in the TableView
        controller.setUpControls();
        assertEquals(controller.getChoicesTable().getItems().get(0), defaultChoice);
    }

    @Test
    public void onStoryPiecesTableNewSelection_ShouldRepopulateTheChoicesTableBasedOnCurrentlySelectedStoryPieceChoices() {
        StoryPiece spTwo = new StoryPiece();
        Choice spTwoChoice = new Choice(defaultStoryPiece, "I'm link to the first StoryPiece!");
        defaultAdventure.addStoryPiece(spTwo);
        spTwo.addChoice(spTwoChoice);

        controller.onStoryPiecesTableNewSelection(defaultStoryPiece, spTwo);
        assertEquals(1, controller.getChoicesTable().getItems().size(),
                "After selecting a new StoryPiece, the Choices table doesn't show exactly one Choice the SP has.");
        assertTrue(controller.getChoicesTable().getItems().contains(spTwoChoice),
                "After selecting a new StoryPiece, the content of the Choices table doesn't equal the Choice of the SP.");
    }
}
