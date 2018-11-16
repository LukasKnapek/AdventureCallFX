package org.mabufudyne.designer.core;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

class Choice implements Serializable {

    private StoryPiece choiceSP;
    private transient SimpleStringProperty text;

    private static String DEFAULT_TEXT = "Go to";

    /** Constructors **/

    public Choice(StoryPiece choiceSP, String text) {
        this.choiceSP = choiceSP;
        this.text = new SimpleStringProperty(text);
    }

    public Choice(StoryPiece choiceSP) {
        this(choiceSP, DEFAULT_TEXT);
    }

    /** Getters and Setters **/

    public String getText() { return text.get(); }

    public void setText(String newText) { this.text.set(newText); }

    public SimpleStringProperty textProperty() { return text; }

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
                Objects.equals(text.get(), choice.text.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceSP, text.get());
    }

    /** Serialization **/

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(getText());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.text = new SimpleStringProperty(in.readUTF());
    }

}
