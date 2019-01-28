package org.mabufudyne.designer.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainWindowController {

    @FXML private VBox overview;
    @FXML private OverviewController overviewController;
    @FXML private StoryPieceViewController storyPieceViewController;
    @FXML private ChoiceViewController choiceViewController;

    public OverviewController getOverviewController() {
        return overviewController;
    }
    public StoryPieceViewController getStoryPieceViewController() { return storyPieceViewController; }
    public ChoiceViewController getChoiceViewController() { return choiceViewController; }

    public void setOverviewController(OverviewController overviewController) {
        this.overviewController = overviewController;
    }

}
