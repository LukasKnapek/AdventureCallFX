package org.mabufudyne.designer.core;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Choice implements Serializable {

    private StoryPiece choiceSP;
    private transient SimpleStringProperty description;

    private static String DEFAULT_TEXT = "Go to";

    /** Constructors **/

    public Choice(StoryPiece choiceSP, String description) {
        this.choiceSP = choiceSP;
        this.description = new SimpleStringProperty(description);
    }

    public Choice(StoryPiece choiceSP) {
        this(choiceSP, DEFAULT_TEXT);
    }

    /** Getters and Setters **/

    public String getDescription() { return description.get(); }

    public void setDescription(String newText) { this.description.set(newText); }

    public SimpleStringProperty descriptionProperty() { return description; }

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
                Objects.equals(description.get(), choice.description.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceSP, description.get());
    }

    /** Serialization **/

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(getDescription());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.description = new SimpleStringProperty(in.readUTF());
    }

}
