package org.mabufudyne.designer.core;

import java.util.ArrayList;
import java.util.Collections;

public class Adventure {

    private static String DEFAULT_NAME = "New Adventure";
    private static String DEFAULT_STORYPIECE_TITLE = "Untitled";
    private static ArrayList<Integer> availableOrders;

    private ArrayList<StoryPiece> storyPieces;
    private String name;

    public Adventure() {
        this(DEFAULT_NAME);
    }

    public Adventure(String name) {
        this.name = name;
        this.storyPieces = new ArrayList<>();
        this.availableOrders = new ArrayList<>();

        this.createNewStoryPiece(DEFAULT_STORYPIECE_TITLE);
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
        StoryPiece newSP = new StoryPiece(title, obtainNextStoryPieceOrder());
        storyPieces.add(newSP);
        return newSP;
    }

    public StoryPiece createNewStoryPiece() {
        return createNewStoryPiece(DEFAULT_STORYPIECE_TITLE);
    }

    private int obtainNextStoryPieceOrder() {
        return availableOrders.isEmpty() ? storyPieces.size() + 1 : availableOrders.remove(0);
    }

    private void freeUpOrder(int order) {
        availableOrders.add(order);
        Collections.sort(availableOrders);
    }

    public void removeStoryPiece(StoryPiece sp) {
        if (storyPieces.size() > 1) {
            freeUpOrder(sp.getOrder());
            this.storyPieces.remove(sp);
        }
    }

    public void switchStoryPieceOrder(StoryPiece firstSP, int newOrder) {
        StoryPiece secondSP = null;

        if (newOrder > storyPieces.size() || newOrder <= 0) {
            throw new IllegalArgumentException("The requested new order is out of range (1-Number of existing StoryPieces).");
        }

        for (StoryPiece sp : storyPieces) {
            if (sp.getOrder() == newOrder) {
                secondSP = sp;
            }
        }

        if (secondSP != null) {
            int temp = firstSP.getOrder();
            firstSP.setOrder(secondSP.getOrder());
            secondSP.setOrder(temp);
        }
    }
}
