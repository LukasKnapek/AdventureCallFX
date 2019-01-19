package org.mabufudyne.designer.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        app.linkAdventure(this);

        addStoryPiece(initialSP);
    }

    /** Getters and Setters **/

    String getName() {
        return this.name;
    }

    void setName(String newName) { this.name = newName; }

    public ObservableList<StoryPiece> getStoryPieces() {
        return this.storyPieces;
    }

    static String getDefaultName() {
        return DEFAULT_NAME;
    }

    Application getParentApp() { return this.parentApp; }

    void setParentApp(Application parentApp) {
        this.parentApp = parentApp;
    }

    /** Private helper methods **/

    private int obtainNextStoryPieceOrder() {
        return availableOrders.isEmpty() ? storyPieces.size() + 1 : availableOrders.remove(0);
    }

    private void freeUpOrder(int order) {
        // TODO: Try to find a better way to keep order in available orders without having to sort them after every insertion
        availableOrders.add(order);
        Collections.sort(availableOrders);
    }

    private ArrayList<Integer> obtainShuffableOrders() {
        // TODO: Use streams?

        return storyPieces.stream()
                .filter(sp -> !sp.isFixed())
                .map(StoryPiece::getOrder)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void reassignStoryPieceOrders(ArrayList<Integer> newOrders) {
        for (StoryPiece sp : storyPieces) {
            if (!sp.isFixed()) sp.setOrder(newOrders.remove(0), false);
        }
    }

    /** Public methods **/

    public void addStoryPiece(StoryPiece sp) {
        sp.setOrder(obtainNextStoryPieceOrder(), false);
        storyPieces.add(sp);
        sp.setAdventure(this);

        performAfterTaskActions();
    }

    public void removeStoryPiece(StoryPiece sp) {
        if (storyPieces.size() > 1) {
            freeUpOrder(sp.getOrder());
            this.storyPieces.remove(sp);

            performAfterTaskActions();
        }
    }

    public void switchStoryPieceOrder(StoryPiece firstSP, int newOrder) {
        // Case 1: New order currently available, but unused
        if (availableOrders.contains(newOrder)) {
            freeUpOrder(firstSP.getOrder());
            firstSP.setOrder(availableOrders.remove(availableOrders.indexOf(newOrder)), false);
        }
        // Case 2: New order assigned to another SP
        else {
            StoryPiece secondSP = storyPieces.stream()
                    .filter(sp -> sp.getOrder() == newOrder)
                    .reduce((a, b) -> { throw new IllegalArgumentException("Multiple StoryPieces with the same order found"); })
                    .get();

            secondSP.setOrder(firstSP.getOrder(), false);
            firstSP.setOrder(newOrder, false);
        }

        performAfterTaskActions();
    }

    /**
     * Return the maximum order that has been used so far (i.e. assigned to a StoryPiece at some point)
     */
    public int getMaxUsedOrder() {
        int maxAvailableOrder = availableOrders.size() > 0 ? Collections.max(availableOrders) : 0;
        int maxUsedOrder = Collections.max(storyPieces.stream()
                .map(StoryPiece::getOrder)
                .collect(toList()));

        return Math.max(maxAvailableOrder, maxUsedOrder);
    }

    void shuffleStoryPieces() {
        ArrayList<Integer> originalOrders = obtainShuffableOrders();
        ArrayList<Integer> shuffableOrders = obtainShuffableOrders();
        boolean sameOrders = true;

        // If there are fewer than two shuffable orders, there is nothing to be done
        if (shuffableOrders.size() <= 1) return;

        while (sameOrders) {
            Collections.shuffle(shuffableOrders);
            sameOrders = shuffableOrders.equals(originalOrders);
        }

        reassignStoryPieceOrders(shuffableOrders);
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
