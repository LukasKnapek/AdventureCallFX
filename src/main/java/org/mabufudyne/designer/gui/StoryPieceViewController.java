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
        TableView<StoryPiece> storyPiecesTable = mainController.getOverviewController().storyPiecesTable;
        storyPiecesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onStoryPiecesTableNewSelection(newValue));

        spinOrder.focusedProperty().addListener(
                (observable, lostFocus, gotFocus) -> { if (lostFocus) onOrderSpinnerFocusLost(); });

        textTitle.focusedProperty().addListener(
                (observable, lostFocus, gotFocus) -> { if (lostFocus) onTitleFieldFocusLost(); });
    }

    public void onStoryPiecesTableNewSelection(StoryPiece selectedSP) {
        // TODO: Save all fields values of the previously selected SP before displaying the values of the newly selected SP
        spinOrder.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, app.getActiveAdventure().getMaxUsedOrder(), selectedSP.getOrder()));
        textTitle.setText(selectedSP.getTitle());
        textStory.setText(selectedSP.getStory());
    }

    public void onOrderSpinnerFocusLost() {
        StoryPiece selectedSP = mainController.getOverviewController().getSelectedStoryPiece();
        app.getActiveAdventure().switchStoryPieceOrder(selectedSP, spinOrder.getValue());
    }

    public void onTitleFieldFocusLost() {
        StoryPiece selectedSP = mainController.getOverviewController().getSelectedStoryPiece();
        selectedSP.setTitle(textTitle.getText());
    }
}
