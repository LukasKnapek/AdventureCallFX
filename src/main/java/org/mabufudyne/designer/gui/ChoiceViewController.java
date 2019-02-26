package org.mabufudyne.designer.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.mabufudyne.designer.core.Choice;
import org.mabufudyne.designer.core.StoryPiece;

import java.io.IOException;

public class ChoiceViewController extends WindowSubController {

    @FXML private TableView<Choice> choicesTable;
    @FXML private TableColumn<Choice, Integer> spOrderColumn;
    @FXML private TableColumn<Choice, String> spTitleColumn;
    @FXML private TableColumn<Choice, String> descriptionColumn;
    @FXML private Button btAddChoice;

    private StoryPiece selectedStoryPiece;

    public void initialize() {
        spOrderColumn.setCellValueFactory(cellData -> cellData.getValue().getStoryPiece().orderProperty().asObject());
        spTitleColumn.setCellValueFactory(cellData -> cellData.getValue().getStoryPiece().titleProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    @Override
    public void setupListeners() {
        OverviewController oc = (OverviewController) mainController.getController("OverviewController");
        TableView<StoryPiece> storyPiecesTable = oc.getStoryPiecesTable();
        storyPiecesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedSP, newSelectedSP) -> onStoryPiecesTableNewSelection(oldSelectedSP, newSelectedSP));

        btAddChoice.setOnAction(event -> onAddChoiceClick());
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
        selectedStoryPiece = newSP;
    }

    public Stage onAddChoiceClick() {
        Stage subStage = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewChoiceWindow.fxml"));
            BorderPane choiceWindow = loader.load();
            NewChoiceWindowController controller = loader.getController();
            controller.setCurrentStoryPiece(selectedStoryPiece);
            controller.loadData(app.getActiveAdventure().getStoryPieces());

            Scene subScene = new Scene(choiceWindow);
            subStage = new Stage();
            controller.setStage(subStage);
            subStage.setScene(subScene);
            subStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return subStage;
    }
}
