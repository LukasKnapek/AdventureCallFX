package org.mabufudyne.designer.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application  {

    private static Application app;

    private Memento currentState;
    private LinkedList<Memento> undoList = new LinkedList<>();
    private LinkedList<Memento> redoList = new LinkedList<>();

    private String propertiesPath = "config.properties";
    private Properties properties = new Properties();

    private static final Logger LOGGER = Logger.getLogger( Application.class.getName() );

    private Application() {}

    public static Application getApp() {
        if (app == null) {
            app = new Application();
        }
        return app;
    }

    public Adventure initialize() throws IOException {
        loadDefaultProperties(propertiesPath);
        Adventure initialAdventure = new Adventure();
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

    public Memento getCurrentState() { return currentState; }

    public LinkedList<Memento> getUndoList() { return undoList; }

    public LinkedList<Memento> getRedoList() { return redoList; }

    public Properties getProperties() {
        return properties;
    }

    public void setPropertiesPath(String path) {
        propertiesPath = path;
    }

    public void saveState() {
        if (properties.getProperty("saveStates") == null || properties.getProperty("saveStates").equals("true")) {
            Memento newState = new Memento(Adventure.getActiveAdventure());
            if (currentState != null) undoList.push(currentState);
            currentState = newState;
        }
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

    public void reset() {
        undoList.clear();
        redoList.clear();
        propertiesPath = "config.properties";
    }
}
