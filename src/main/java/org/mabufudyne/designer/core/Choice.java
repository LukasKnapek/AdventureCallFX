package org.mabufudyne.designer.core;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return Objects.equals(choiceSP, choice.choiceSP) &&
                Objects.equals(text, choice.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceSP, text);
    }

    public static String getDefaultText() {
        return DEFAULT_TEXT;
    }


}
