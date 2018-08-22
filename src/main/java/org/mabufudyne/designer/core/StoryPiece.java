package org.mabufudyne.designer.core;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class StoryPiece implements Serializable {

    private String title;
    private String story;
    private int order;
    private boolean fixed;
    private String color;

    private ArrayList<Choice> choices;

    /** Constructors **/

    public StoryPiece(String title, int order) {
        this.title = title;
        this.story = "";
        this.order = order;
        this.fixed = false;

        this.choices = new ArrayList<>();
    }

    /** Getters and Setters **/

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
        Application.getApp().performAfterTaskActions();
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
        Application.getApp().performAfterTaskActions();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int newOrder) { setOrder(newOrder, true); }

    public void setOrder(int newOrder, boolean performAfterTaskActions) {
        order = newOrder;
        if (performAfterTaskActions)
            Application.getApp().performAfterTaskActions();
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
        Application.getApp().performAfterTaskActions();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        Application.getApp().performAfterTaskActions();
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    /** Public methods **/

    public void addChoice(Choice choice) {

        for (Choice existingChoice : choices) {
            if (existingChoice.getStoryPiece() == choice.getStoryPiece()) return;
        }

        if (choice.getStoryPiece() != this) {
            choices.add(choice);

            Application.getApp().performAfterTaskActions();
        }
    }

    public void removeChoice(Choice choice) {
        if (choices.contains(choice)) {
            choices.remove(choice);

            Application.getApp().performAfterTaskActions();
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
        return order == that.order &&
                fixed == that.fixed &&
                Objects.equals(title, that.title) &&
                Objects.equals(story, that.story) &&
                Objects.equals(color, that.color) &&
                Objects.equals(choices, that.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, story, order, fixed, color, choices);
    }


}
