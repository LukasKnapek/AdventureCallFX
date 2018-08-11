package org.mabufudyne.designer.core;

import java.util.Stack;

public class Application {

    private static Memento currentState;
    private static Stack<Memento> undoStack = new Stack<>();
    private static Stack<Memento> redoStack = new Stack<>();

    private Application() {}

    public static void initialize() {
        Adventure initialAdventure = new Adventure();
        Memento initialState = new Memento(initialAdventure);
        currentState = initialState;
    }

    public static Memento getCurrentState() { return currentState; }

    public static Stack<Memento> getUndoStack() { return undoStack; }

    public static Stack<Memento> getRedoStack() { return redoStack; }


    public static void saveState() {
        Memento newState = new Memento(Adventure.getActiveAdventure());
        undoStack.push(currentState);
        currentState = newState;
    }

    public static void undo() {
        redoStack.push(currentState);
        currentState = undoStack.pop();
    }

    public static void redo() {
        undoStack.push(currentState);
        currentState = redoStack.pop();
    }

}
