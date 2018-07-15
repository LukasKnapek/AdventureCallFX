package org.mabufudyne.designer.core;

import java.util.ArrayList;

public class Adventure {

    private static String DEFAULT_NAME = "New Adventure";
    private static String DEFAULT_STORYPIECE_TITLE = "Untitled";
    private static int nextStoryPieceOrder;

    private ArrayList<StoryPiece> storyPieces;
    private String name;

    public Adventure() {
        this(DEFAULT_NAME);
    }

    public Adventure(String name) {
        this.name = name;
        this.storyPieces = new ArrayList<>();
        nextStoryPieceOrder = 1;

        StoryPiece initialSP = new StoryPiece(DEFAULT_STORYPIECE_TITLE, nextStoryPieceOrder);
    }

    public String getName() {
        return this.name;
    }

    public  ArrayList<StoryPiece> getStoryPieces() {
        return this.storyPieces;
    }

    public static String getDefaultName() {
        return DEFAULT_NAME;
    }

    public static String getDefaultStoryPieceTitle() { return DEFAULT_STORYPIECE_TITLE; }

    public StoryPiece createNewStoryPiece(String title) {
        StoryPiece newSP = new StoryPiece(title, nextStoryPieceOrder);
        storyPieces.add(newSP);
        nextStoryPieceOrder++;
        return newSP;
    }

    public StoryPiece createNewStoryPiece() {
        return createNewStoryPiece(DEFAULT_STORYPIECE_TITLE);
    }

    public void removeStoryPiece(StoryPiece sp) {
        if (this.storyPieces.contains(sp)) {
            if (storyPieces.size() > 1) {
                this.storyPieces.remove(sp);
                nextStoryPieceOrder--;
            }
        }
        else {
            throw new IllegalArgumentException("Adventure does not contain the given StoryPiece.");
        }
    }

    public void switchStoryPieceOrder(StoryPiece firstSP, int newOrder) {
        StoryPiece secondSP = null;

        if (newOrder > storyPieces.size()) {
            throw new IllegalArgumentException("The requested new order is out of range (not assigned to any StoryPiece yet).");
        }

        for (StoryPiece sp : storyPieces) {
            if (sp.getOrder() == newOrder) {
                secondSP = sp;
            }
        }

        int temp = firstSP.getOrder();
        firstSP.setOrder(secondSP.getOrder());
        secondSP.setOrder(temp);
    }
}
