package org.mabufudyne.designer.core;

import java.util.ArrayList;

public class StoryPiece {

    private String title;
    private String story;
    private int order;
    private ArrayList<Choice> choices;

    public StoryPiece(String title, int order) {
        this.title = title;
        this.story = "";
        this.order = order;
        this.choices = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getStory() {
        return story;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int newOrder) {
        order = newOrder;
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
