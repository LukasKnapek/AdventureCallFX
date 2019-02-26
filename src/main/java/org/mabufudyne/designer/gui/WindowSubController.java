package org.mabufudyne.designer.gui;

import org.mabufudyne.designer.core.Application;

public abstract class WindowSubController {

    protected MainWindowController mainController;
    protected Application app;

    public void setMainController(MainWindowController mainController) {
        this.mainController = mainController;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    /**
     * Post-initialisation configuration - use the injected Application and MainWindowController references to
     * populate Controls or set up some other stuff dependent on other Controllers
     */
    public void setupListeners() {}

    public void populateControls() {}
}
