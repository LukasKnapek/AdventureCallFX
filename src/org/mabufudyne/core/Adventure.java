package org.mabufudyne.core;

import java.util.ArrayList;

public class Adventure {

    private static String DEFAULT_NAME = "New Adventure";

    private ArrayList<StoryPiece> storyPieces;
    private String name;

    public Adventure() {
        this(DEFAULT_NAME);
    }

    public Adventure(String name) {
        this.name = name;
        this.storyPieces = new ArrayList<>();

        StoryPiece initialSP = new StoryPiece();
        storyPieces.add(initialSP);
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

    public void addStoryPiece(StoryPiece sp) {
        if (!storyPieces.contains(sp)) {
            this.storyPieces.add(sp);
        }
    }

    public void removeStoryPiece(StoryPiece sp) {
        if (this.storyPieces.contains(sp)) {
            if (storyPieces.size() > 1) {
                this.storyPieces.remove(sp);
            }
        }
        else {
            throw new IllegalArgumentException("Adventure does not contain the given StoryPiece.");
        }
    }


}
