package org.mabufudyne.designer.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainWindowController {

    @FXML private VBox overview;
    @FXML private OverviewController overviewController;

    public OverviewController getOverviewController() {
        return overviewController;
    }
}
