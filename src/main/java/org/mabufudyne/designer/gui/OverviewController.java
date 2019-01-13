package org.mabufudyne.designer.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.mabufudyne.designer.core.StoryPiece;

public class OverviewController extends WindowSubController {

    public TableView<StoryPiece> storyPiecesTable;
    @FXML private TableColumn<StoryPiece, Integer> orderColumn;
    @FXML private TableColumn<StoryPiece, String> titleColumn;

    public void initialize() {
        orderColumn.setCellValueFactory(cellData -> cellData.getValue().orderProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    }

    public void onAddStoryPieceClick() {
        app.getActiveAdventure().addStoryPiece(new StoryPiece());
    }

    public void onRemoveStoryPieceClick() {
        // TODO: Disable the remove button if there is only one StoryPiece left
        app.getActiveAdventure().removeStoryPiece(storyPiecesTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void setUpControls() {
        storyPiecesTable.setItems(app.getActiveAdventure().getStoryPieces());
    }

    public StoryPiece getSelectedStoryPiece() {
        return storyPiecesTable.getSelectionModel().getSelectedItem();
    }
}
