package org.mabufudyne.designer.core;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class StoryPiece {

    private String title;
    private String story;
    private int order;
    private boolean fixed;
    private Color color;

    private ArrayList<Choice> choices;

    public StoryPiece(String title, int order) {
        this.title = title;
        this.story = "";
        this.order = order;
        this.fixed = false;

        this.choices = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int newOrder) {
        order = newOrder;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public void addChoice(Choice choice) {

        for (Choice existingChoice : choices) {
            if (existingChoice.getStoryPiece() == choice.getStoryPiece()) return;
        }

        if (choice.getStoryPiece() != this) {
            choices.add(choice);
        }
    }

    public void removeChoice(Choice choice) {
        if (choices.contains(choice)) {
            choices.remove(choice);
        }
        else {
            throw new IllegalArgumentException("StoryPiece does not contain the given Choice.");
        }
    }




}
