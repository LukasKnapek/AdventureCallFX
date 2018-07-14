package org.mabufudyne.designer.core;

public class Choice {

    private StoryPiece choiceSP;
    private String text;

    private static String DEFAULT_TEXT = "Go to";

    public Choice(StoryPiece choiceSP, String text) {
        this.choiceSP = choiceSP;
        this.text = text;
    }

    public Choice(StoryPiece choiceSP) {
        this(choiceSP, DEFAULT_TEXT);
    }

    public String getText() {
        return text;
    }

    public StoryPiece getStoryPiece() {
        return choiceSP;
    }

    public static String getDefaultText() {
        return DEFAULT_TEXT;
    }


}
