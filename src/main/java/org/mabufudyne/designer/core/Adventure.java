package org.mabufudyne.designer.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class Adventure implements Serializable {

    private static String DEFAULT_NAME = "New Adventure";

    private transient ObservableList<StoryPiece> storyPieces;
    private ArrayList<Integer> availableOrders;

    private String name;
    private transient Application parentApp;

    /** Constructors **/

    public Adventure(Application app, StoryPiece initialSP) {
        this(app, initialSP, DEFAULT_NAME);
    }

    public Adventure(Application app, StoryPiece initialSP, String name) {
        this.name = name;
        this.storyPieces = FXCollections.observableArrayList();
        this.availableOrders = new ArrayList<>();
        this.parentApp = app;

        addStoryPiece(initialSP);
    }

    /** Getters and Setters **/

    String getName() {
        return this.name;
    }

    void setName(String newName) { this.name = newName; }

    ObservableList<StoryPiece> getStoryPieces() {
        return this.storyPieces;
    }

    static String getDefaultName() {
        return DEFAULT_NAME;
    }

    void setParentApp(Application parentApp) {
        this.parentApp = parentApp;
    }

    /** Private helper methods **/

    private void sortStoryPiecesByOrder() {
        Collections.sort(storyPieces, Comparator.comparing(StoryPiece::getOrder));
    }

    private int obtainNextStoryPieceOrder() {
        return availableOrders.isEmpty() ? storyPieces.size() + 1 : availableOrders.remove(0);
    }

    private void freeUpOrder(int order) {
        availableOrders.add(order);
        Collections.sort(availableOrders);
    }

    private ArrayList<Integer> obtainShuffableOrders() {
        ArrayList<Integer> shuffableOrders = new ArrayList<>();

        for (StoryPiece sp : storyPieces) {
            if (!sp.isFixed()) shuffableOrders.add(sp.getOrder());
        }

        return shuffableOrders;
    }

    private void reassignStoryPieceOrders(ArrayList<Integer> newOrders) {
        for (StoryPiece sp : storyPieces) {
            if (!sp.isFixed()) sp.setOrder(newOrders.remove(0), false);
        }
    }

    /** Public methods **/

    void addStoryPiece(StoryPiece sp) {
        sp.setOrder(obtainNextStoryPieceOrder(), false);
        storyPieces.add(sp);
        sp.setAdventure(this);

        performAfterTaskActions();
    }

    void removeStoryPiece(StoryPiece sp) {
        if (storyPieces.size() > 1) {
            freeUpOrder(sp.getOrder());
            this.storyPieces.remove(sp);

            performAfterTaskActions();
        }
    }

    void switchStoryPieceOrder(StoryPiece firstSP, int newOrder) {
        if (newOrder > storyPieces.size() || newOrder <= 0) {
            throw new IllegalArgumentException("The requested new order is out of range (1-Number of existing StoryPieces).");
        }

        for (StoryPiece sp : storyPieces) {
            if (sp.getOrder() == newOrder) {
                int temp = firstSP.getOrder();
                firstSP.setOrder(sp.getOrder(), false);
                sp.setOrder(temp, false);

                performAfterTaskActions();
                break;
            }
        }
    }

    void shuffleStoryPieces() {
        ArrayList<StoryPiece> storyPiecesOriginalOrder = new ArrayList<>(storyPieces);
        ArrayList<Integer> shuffableOrders;
        boolean sameResultOrder = true;

        while (sameResultOrder) {
            shuffableOrders = obtainShuffableOrders();
            // If there is less than two shuffable orders, there is nothing to be done
            if (shuffableOrders.size() <= 1) return;

            Collections.shuffle(shuffableOrders);
            reassignStoryPieceOrders(shuffableOrders);
            sortStoryPiecesByOrder();

            // If the result of the shuffle is the same StoryPiece order as before, we will repeat the process
            sameResultOrder = storyPiecesOriginalOrder.equals(storyPieces);
        }

        performAfterTaskActions();


    }

    void performAfterTaskActions() {
        parentApp.performAfterTaskActions();
    }

    /** Overriden methods **/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adventure adventure = (Adventure) o;
        return Objects.equals(storyPieces, adventure.storyPieces) &&
                Objects.equals(name, adventure.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(storyPieces, name);
    }

    /** Serialization **/

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(new ArrayList<>(storyPieces));
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.storyPieces = FXCollections.observableArrayList((ArrayList<StoryPiece>) in.readObject());
    }

}
