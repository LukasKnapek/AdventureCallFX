package org.mabufudyne.designer.core;

import java.util.ArrayList;

public class StoryPiece {

    private static String DEFAULT_TITLE = "Untitled";
    private static int nextOrder = 1;

    private String title;
    private String story;
    private int order;
    private ArrayList<StoryPiece> choices;

    public StoryPiece(String title) {
        this.title = title;
        this.story = "";
        this.order = nextOrder;
        this.choices = new ArrayList<>();
        nextOrder++;
    }

    public StoryPiece() {
        this(DEFAULT_TITLE);
    }

    public String getTitle() {
        return title;
    }

    public String getStory() {
        return story;
    }

    public static String getDefaultTitle() {
        return DEFAULT_TITLE;
    }

    public int getOrder() {
        return order;
    }

    public static void setNextOrder(int next) {
        nextOrder = next;
    }

    public ArrayList<StoryPiece> getChoices() {
        return choices;
    }

    public void addChoice(StoryPiece choice) {
        if (!choices.contains(choice) && choice != this) {
            choices.add(choice);
        }
    }

    public void removeChoice(StoryPiece choice) {
        if (choices.contains(choice)) {
            choices.remove(choice);
        }
        else {
            throw new IllegalArgumentException("StoryPiece does not contain the given choice.");
        }
    }




}
