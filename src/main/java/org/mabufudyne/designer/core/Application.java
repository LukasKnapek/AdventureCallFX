package org.mabufudyne.designer.core;

import java.util.LinkedList;
import java.util.Properties;

class Application  {

    private Adventure activeAdventure;
    private Properties properties;
    private LinkedList<Memento> undoList;
    private LinkedList<Memento> redoList;

    private Memento currentState;

    Application(Properties props) {
        this.undoList = new LinkedList<>();
        this.redoList = new LinkedList<>();
        this.properties = props;
    }

    Memento getCurrentState() { return currentState; }

    LinkedList<Memento> getUndoList() { return undoList; }

    LinkedList<Memento> getRedoList() { return redoList; }

    Properties getProperties() {
        return properties;
    }

    Adventure getActiveAdventure() {
        return activeAdventure;
    }

    void setActiveAdventure(Adventure activeAdventure) {
        this.activeAdventure = activeAdventure;
    }

    void saveState() {
        if (properties.getProperty("saveStates") == null || properties.getProperty("saveStates").equals("true")) {
            Memento newState = new Memento(activeAdventure);
            if (currentState != null) undoList.push(currentState);
            currentState = newState;
        }
    }

    //TODO: For Undo and Redo, change the active adventure (and its parent app) after loading the state
    void undo() {
        redoList.push(currentState);
        currentState = undoList.pop();
    }

    void redo() {
        undoList.push(currentState);
        currentState = redoList.pop();
    }

    void performAfterTaskActions() {
        this.saveState();
    }
}
