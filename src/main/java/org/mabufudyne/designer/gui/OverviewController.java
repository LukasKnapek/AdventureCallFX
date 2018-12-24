package org.mabufudyne.designer.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.mabufudyne.designer.core.Application;
import org.mabufudyne.designer.core.StoryPiece;

import javax.annotation.Resources;
import java.net.URL;

public class OverviewController {
    public TableView<StoryPiece> storyPiecesTable;
    @FXML private TableColumn<StoryPiece, Integer> orderColumn;
    @FXML private TableColumn<StoryPiece, String> titleColumn;

    private Application app;

    public void setApp(Application app) {
        this.app = app;
    }

    public void initialize() {
        orderColumn.setCellValueFactory(cellData -> cellData.getValue().orderProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    }

    public void populateStoryPieceTable(ObservableList<StoryPiece> data) {
        storyPiecesTable.setItems(data);
    }

    public void onAddStoryPieceClick() {
        app.getActiveAdventure().addStoryPiece(new StoryPiece());
    }

    public void onRemoveStoryPieceClick() {
        app.getActiveAdventure().removeStoryPiece(storyPiecesTable.getSelectionModel().getSelectedItem());
    }
}
