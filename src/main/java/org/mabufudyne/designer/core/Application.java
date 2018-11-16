package org.mabufudyne.designer.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class Application  {

    private static Application app;

    private Memento currentState;
    private LinkedList<Memento> undoList = new LinkedList<>();
    private LinkedList<Memento> redoList = new LinkedList<>();

    private String propertiesPath = "config.properties";
    private Properties properties = new Properties();

    private static final Logger LOGGER = Logger.getLogger( Application.class.getName() );

    private Application() {}

    static Application getApp() {
        if (app == null) {
            app = new Application();
        }
        return app;
    }

    Adventure initialize() throws IOException {
        loadDefaultProperties(propertiesPath);

        StoryPiece initialSP = new StoryPiece();
        Adventure initialAdventure = new Adventure(initialSP);
        Adventure.setActiveAdventure(initialAdventure);

        return initialAdventure;
    }

    private void loadDefaultProperties(String path) throws IOException {
        try {
            FileInputStream in = new FileInputStream(path);
            properties.load(in);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while loading default properties:");
            throw e;
        }
    }

    Memento getCurrentState() { return currentState; }

    LinkedList<Memento> getUndoList() { return undoList; }

    LinkedList<Memento> getRedoList() { return redoList; }

    Properties getProperties() {
        return properties;
    }

    void setPropertiesPath(String path) {
        propertiesPath = path;
    }

    void saveState() {
        if (properties.getProperty("saveStates") == null || properties.getProperty("saveStates").equals("true")) {
            Memento newState = new Memento(Adventure.getActiveAdventure());
            if (currentState != null) undoList.push(currentState);
            currentState = newState;
        }
    }

    void undo() {
        redoList.push(currentState);
        currentState = undoList.pop();
    }

    void redo() {
        undoList.push(currentState);
        currentState = redoList.pop();
    }

    void performAfterTaskActions() {
        app.saveState();
    }

    void reset() {
        undoList.clear();
        redoList.clear();
        propertiesPath = "config.properties";
    }
}
