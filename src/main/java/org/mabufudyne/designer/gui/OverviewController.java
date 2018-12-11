package org.mabufudyne.designer.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.mabufudyne.designer.core.Application;
import org.mabufudyne.designer.core.StoryPiece;

import java.net.URL;

public class OverviewController {
    @FXML
    private TableView<StoryPiece> storyPiecesTable;
    @FXML
    private TableColumn<StoryPiece, Integer> orderColumn;
    @FXML
    private TableColumn<StoryPiece, String> titleColumn;

    public void initialize() {
        orderColumn.setCellValueFactory(cellData -> cellData.getValue().orderProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    }

    public void populateStoryPieceTable(ObservableList<StoryPiece> data) {
        storyPiecesTable.setItems(data);
    }
}
