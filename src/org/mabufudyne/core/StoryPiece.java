package org.mabufudyne.core;

public class StoryPiece {

    private static String DEFAULT_TITLE = "Untitled";
    private static int nextOrder = 1;

    private String title;
    private String story;
    private int order;

    public StoryPiece(String title) {
        this.title = title;
        this.story = "";
        this.order = nextOrder;
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




}
