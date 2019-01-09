package org.mabufudyne.designer.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainWindowController {

    @FXML private VBox overview;
    public OverviewController overviewController;
    @FXML private StoryPieceViewController storyPieceViewController;

    public OverviewController getOverviewController() {
        return overviewController;
    }
    public StoryPieceViewController getStoryPieceViewController() { return storyPieceViewController; }
}
