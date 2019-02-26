package org.mabufudyne.designer.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mabufudyne.designer.core.Choice;
import org.mabufudyne.designer.core.StoryPiece;

import java.util.List;
import java.util.stream.Collectors;

public class NewChoiceWindowController extends WindowSubController {

    @FXML private TableView<StoryPiece> storyPiecesTable;
    @FXML private TableColumn<StoryPiece, Integer> orderColumn;
    @FXML private TableColumn<StoryPiece, String> titleColumn;
    @FXML private TextField textChoiceDescription;
    @FXML private Button btnAddNewChoice;
    @FXML private Button btnCancel;

    private StoryPiece currentStoryPiece;
    private Stage stage;

    /**
     * Constructors
     **/

    public NewChoiceWindowController() {
    }

    public NewChoiceWindowController(TableView<StoryPiece> storyPiecesTable,
                                     TableColumn<StoryPiece, Integer> orderColumn,
                                     TableColumn<StoryPiece, String> titleColumn,
                                     TextField textChoiceDescription,
                                     Button btnAddNewChoice,
                                     Button btnCancel) {
        this.storyPiecesTable = storyPiecesTable;
        this.orderColumn = orderColumn;
        this.titleColumn = titleColumn;
        this.textChoiceDescription = textChoiceDescription;
        this.btnAddNewChoice = btnAddNewChoice;
        this.btnCancel = btnCancel;
    }

    public void loadData(ObservableList<StoryPiece> storyPieces) {
        List<StoryPiece> invalidChoices = currentStoryPiece.getChoices()
                .stream()
                .map(Choice::getStoryPiece)
                .collect(Collectors.toList());
        invalidChoices.add(currentStoryPiece);

        List<StoryPiece> valid = storyPieces.stream()
                .filter(storyPiece -> !invalidChoices.contains(storyPiece))
                .collect(Collectors.toList());
        ObservableList<StoryPiece> validChoices = FXCollections.observableArrayList(valid);

        storyPiecesTable.setItems(validChoices);
    }

    public void initialize() {
        orderColumn.setCellValueFactory(cellData -> cellData.getValue().orderProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        btnAddNewChoice.setOnAction(event -> onAddNewChoiceClick());
        btnCancel.setOnAction(event -> onCancelClick());
        storyPiecesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onStoryPieceSelect());

        btnAddNewChoice.setDisable(true);
    }

    void onCancelClick() {
        stage.close();
    }

    private void onStoryPieceSelect() {
        btnAddNewChoice.setDisable(false);
    }

    void onAddNewChoiceClick() {
        String choiceDescription = textChoiceDescription.getText();
        StoryPiece choiceSP = storyPiecesTable.getSelectionModel().getSelectedItem();

        currentStoryPiece.addChoice(new Choice(choiceSP, choiceDescription));
        if (stage != null) stage.close();
    }


    /** Getters and Setters **/

    public void setCurrentStoryPiece(StoryPiece currentStoryPiece) {
        this.currentStoryPiece = currentStoryPiece;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
