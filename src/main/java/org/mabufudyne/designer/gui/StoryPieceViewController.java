package org.mabufudyne.designer.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.mabufudyne.designer.core.StoryPiece;

public class StoryPieceViewController extends WindowSubController {
    @FXML private Spinner<Integer> spinOrder;
    @FXML private TextField textTitle;
    @FXML private TextArea textStory;

    public StoryPieceViewController(Spinner<Integer> spinOrder, TextField textTitle, TextArea textStory) {
        this.spinOrder = spinOrder;
        this.textTitle = textTitle;
        this.textStory = textStory;
    }

    public StoryPieceViewController() {}

    @Override
    public void setUpControls() {
        spinOrder.valueProperty().addListener((observable, oldVal, newVal) -> onOrderSpinnerValueChange());

        TableView<StoryPiece> storyPiecesTable = mainController.getOverviewController().storyPiecesTable;
        storyPiecesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedSP, newSelectedSP) -> onStoryPiecesTableNewSelection(oldSelectedSP, newSelectedSP));
    }

    public void onStoryPiecesTableNewSelection(StoryPiece oldSP, StoryPiece newSP) {
        spinOrder.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, app.getActiveAdventure().getMaxUsedOrder(), newSP.getOrder()));

        if (oldSP != null) {
            textTitle.textProperty().unbindBidirectional(oldSP.titleProperty());
            textStory.textProperty().unbindBidirectional(oldSP.storyProperty());
        }
        textTitle.textProperty().bindBidirectional(newSP.titleProperty());
        textStory.textProperty().bindBidirectional(newSP.storyProperty());
    }

    public void onOrderSpinnerValueChange() {
        StoryPiece selectedSP = mainController.getOverviewController().getSelectedStoryPiece();
        app.getActiveAdventure().switchStoryPieceOrder(selectedSP, spinOrder.getValue());
    }
}
