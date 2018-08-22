package org.mabufudyne.designer.core;

import java.io.Serializable;
import java.util.Objects;

public class Choice implements Serializable {

    private StoryPiece choiceSP;
    private String text;

    private static String DEFAULT_TEXT = "Go to";

    /** Constructors **/

    public Choice(StoryPiece choiceSP, String text) {
        this.choiceSP = choiceSP;
        this.text = text;
    }

    public Choice(StoryPiece choiceSP) {
        this(choiceSP, DEFAULT_TEXT);
    }

    /** Getters and Setters **/

    public String getText() {
        return text;
    }

    public void setText(String newText) { this.text = newText; }

    public StoryPiece getStoryPiece() {
        return choiceSP;
    }

    /** Private helper methods **/

    public static String getDefaultText() {
        return DEFAULT_TEXT;
    }

    /** Overriden methods **/

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

}
