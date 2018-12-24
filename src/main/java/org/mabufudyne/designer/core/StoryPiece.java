package org.mabufudyne.designer.core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class StoryPiece implements Serializable {

    private static String DEFAULT_TITLE = "Untitled";

    private Adventure adventure;

    private transient SimpleStringProperty title;
    private transient SimpleIntegerProperty order;
    private transient SimpleBooleanProperty fixed;
    private String story;
    private String color;

    private transient ObservableList<Choice> choices;

    /** Constructors **/

    public StoryPiece() {
        this(DEFAULT_TITLE);
    }

    public StoryPiece(String title) {
        this.title = new SimpleStringProperty(title);
        this.order = new SimpleIntegerProperty();
        this.fixed = new SimpleBooleanProperty(false);
        this.story = "";

        this.choices = FXCollections.observableArrayList();
    }

    /** Getters and Setters **/

    String getTitle() {
        return title.get();
    }

    void setTitle(String newTitle) {
        this.title.set(newTitle);
        adventure.performAfterTaskActions();
    }

    public SimpleStringProperty titleProperty() { return title; }

    String getStory() {
        return story;
    }

    void setStory(String story) {
        this.story = story;
        adventure.performAfterTaskActions();
    }

    public int getOrder() {
        return order.get();
    }

    void setOrder(int newOrder) { setOrder(newOrder, true); }

    void setOrder(int newOrder, boolean performAfterTaskActions) {
        order.set(newOrder);
        if (performAfterTaskActions)
            adventure.performAfterTaskActions();
    }

    public SimpleIntegerProperty orderProperty() { return order; }

    boolean isFixed() {
        return fixed.get();
    }

    void setFixed(boolean fixed) {
        this.fixed.set(fixed);
        adventure.performAfterTaskActions();
    }

    SimpleBooleanProperty fixedProperty() { return fixed; }

    String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
        adventure.performAfterTaskActions();
    }

    ObservableList<Choice> getChoices() {
        return choices;
    }

    public void setAdventure(Adventure adventure) {
        this.adventure = adventure;
    }

    /** Public methods **/

    void addChoice(Choice choice) {

        for (Choice existingChoice : choices) {
            if (existingChoice.getStoryPiece() == choice.getStoryPiece()) return;
        }

        if (choice.getStoryPiece() != this) {
            choices.add(choice);

            adventure.performAfterTaskActions();
        }
    }

    void removeChoice(Choice choice) {
        if (choices.contains(choice)) {
            choices.remove(choice);

            adventure.performAfterTaskActions();
        }
        else {
            throw new IllegalArgumentException("StoryPiece does not contain the given Choice.");
        }
    }

    /** Overriden methods **/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryPiece that = (StoryPiece) o;
        return order.get() == that.order.get() &&
                fixed.get() == that.fixed.get() &&
                Objects.equals(title.get(), that.title.get()) &&
                Objects.equals(story, that.story) &&
                Objects.equals(color, that.color) &&
                Objects.equals(choices, that.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.get(), story, order.get(), fixed.get(), color, choices);
    }

    /** Serialization **/

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(title.get());
        out.writeInt(order.get());
        out.writeBoolean(fixed.get());
        out.writeObject(new ArrayList<>(choices));
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.title = new SimpleStringProperty(in.readUTF());
        this.order = new SimpleIntegerProperty(in.readInt());
        this.fixed = new SimpleBooleanProperty(in.readBoolean());
        this.choices = FXCollections.observableArrayList((ArrayList<Choice>) in.readObject());
    }
}
