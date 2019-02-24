package org.mabufudyne.designer.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.mabufudyne.designer.core.StoryPiece;

public class NewChoiceWindowController {

    @FXML private TableView<StoryPiece> storyPiecesTable;
    @FXML private TableColumn<StoryPiece, Integer> orderColumn;
    @FXML private TableColumn<StoryPiece, String> titleColumn;

    public void loadData(ObservableList<StoryPiece> storyPieces) {
        storyPiecesTable.setItems(storyPieces);
    }

    public void initialize() {
        orderColumn.setCellValueFactory(cellData -> cellData.getValue().orderProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    }
}
