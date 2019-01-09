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
        // TableView doesn't emit events on row selection directly
        // Instead, we start observing its selection model and call event handler on each change
        storyPiecesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onStoryPiecesTableNewSelection(newValue));

        spinOrder.focusedProperty().addListener(
                (observable, oldValue, newValue) -> onOrderSpinnerFocusLost(newValue));
    }


    public void onStoryPiecesTableNewSelection(StoryPiece selectedSP) {
        spinOrder.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, app.getActiveAdventure().getMaxUsedOrder(), selectedSP.getOrder()));
        textTitle.clear();
        textTitle.insertText(0, selectedSP.getTitle());
        textStory.clear();
        textStory.insertText(0, selectedSP.getStory());
    }

    public void onOrderSpinnerFocusLost(Boolean focusGained) {
        StoryPiece selectedSP = mainController.getOverviewController().storyPiecesTable.getSelectionModel().getSelectedItem();
        app.getActiveAdventure().switchStoryPieceOrder(selectedSP, spinOrder.getValue());
    }

}
