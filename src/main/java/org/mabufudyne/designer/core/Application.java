package org.mabufudyne.designer.core;

import java.util.LinkedList;

public class Application {

    private static Application app;

    private Memento currentState;
    private LinkedList<Memento> undoList = new LinkedList<>();
    private LinkedList<Memento> redoList = new LinkedList<>();

    private Application() {}

    public static Application getApp() {
        if (app == null) {
            app = new Application();
        }
        return app;
    }

    public void initialize() {
        Adventure initialAdventure = new Adventure();
        Memento initialState = new Memento(initialAdventure);
        currentState = initialState;
    }

    public Memento getCurrentState() { return currentState; }

    public LinkedList<Memento> getUndoList() { return undoList; }

    public LinkedList<Memento> getRedoList() { return redoList; }


    public void saveState() {
        Memento newState = new Memento(Adventure.getActiveAdventure());
        undoList.push(currentState);
        currentState = newState;
    }

    public void undo() {
        redoList.push(currentState);
        currentState = undoList.pop();
    }

    public void redo() {
        undoList.push(currentState);
        currentState = redoList.pop();
    }

    public void performAfterTaskActions() {
        app.saveState();
    }

    public void resetStateHistory() {
        undoList.clear();
        redoList.clear();
    }

}
