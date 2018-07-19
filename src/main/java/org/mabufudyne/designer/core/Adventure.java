package org.mabufudyne.designer.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Adventure {

    private static String DEFAULT_NAME = "New Adventure";
    private static String DEFAULT_STORYPIECE_TITLE = "Untitled";
    private static ArrayList<Integer> availableOrders = new ArrayList<>();

    private ArrayList<StoryPiece> storyPieces;
    private String name;

    private void sortStoryPiecesByOrder() {
        Collections.sort(storyPieces, Comparator.comparing(StoryPiece::getOrder));
    }

    public Adventure() {
        this(DEFAULT_NAME);
    }

    public Adventure(String name) {
        this.name = name;
        this.storyPieces = new ArrayList<>();

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
        sortStoryPiecesByOrder();
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
        if (newOrder > storyPieces.size() || newOrder <= 0) {
            throw new IllegalArgumentException("The requested new order is out of range (1-Number of existing StoryPieces).");
        }

        for (StoryPiece sp : storyPieces) {
            if (sp.getOrder() == newOrder) {
                int temp = firstSP.getOrder();
                firstSP.setOrder(sp.getOrder());
                sp.setOrder(temp);
                return;
            }
        }
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
            if (!sp.isFixed()) sp.setOrder(newOrders.remove(0));
        }
    }

    public void shuffleStoryPieces() {
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


    }
}
