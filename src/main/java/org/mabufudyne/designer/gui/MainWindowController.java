package org.mabufudyne.designer.gui;

import java.util.HashMap;

public class MainWindowController {

    private HashMap<String, WindowSubController> controllers;

    /** Getters and Setters **/

    public HashMap<String, WindowSubController> getControllers() {
        return controllers;
    }

    public void setControllers(HashMap<String, WindowSubController> controllers) {
        this.controllers = controllers;
    }

    public WindowSubController getController(String name) {
        return controllers.get(name);
    }

    public void setController(String name, WindowSubController controller) {
        if (controllers == null) controllers = new HashMap<>();
        controllers.put(name, controller);
    }
}
