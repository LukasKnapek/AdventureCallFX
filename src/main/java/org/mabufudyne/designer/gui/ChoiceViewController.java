package org.mabufudyne.designer.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.mabufudyne.designer.core.Choice;
import org.mabufudyne.designer.core.StoryPiece;

public class ChoiceViewController extends WindowSubController {

    @FXML private TableView<Choice> choicesTable;
    @FXML private TableColumn<Choice, Integer> spOrderColumn;
    @FXML private TableColumn<Choice, String> spTitleColumn;
    @FXML private TableColumn<Choice, String> descriptionColumn;

    public void initialize() {
        spOrderColumn.setCellValueFactory(cellData -> cellData.getValue().getStoryPiece().orderProperty().asObject());
        spTitleColumn.setCellValueFactory(cellData -> cellData.getValue().getStoryPiece().titleProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    @Override
    public void setUpControls() {
        choicesTable.setItems(mainController.getOverviewController().getSelectedStoryPiece().getChoices());

        // Set up StoryPieces table selection listener, select an item so the View fields are populated from the start
        TableView<StoryPiece> storyPiecesTable = mainController.getOverviewController().getStoryPiecesTable();
        storyPiecesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedSP, newSelectedSP) -> onStoryPiecesTableNewSelection(oldSelectedSP, newSelectedSP));
    }

    /** Getters and Setters **/

    public TableView<Choice> getChoicesTable() {
        return choicesTable;
    }

    public void setChoicesTable(TableView<Choice> choicesTable) {
        this.choicesTable = choicesTable;
    }

    /** Event handlers **/

    void onStoryPiecesTableNewSelection(StoryPiece oldSP, StoryPiece newSP) {
        choicesTable.setItems(newSP.getChoices());
    }

    public void onAddChoiceClick() {

    }
}
