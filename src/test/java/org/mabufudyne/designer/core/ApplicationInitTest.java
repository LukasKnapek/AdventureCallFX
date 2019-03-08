package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.mabufudyne.designer.gui.MainWindowController;
import org.mabufudyne.designer.gui.StoryPieceViewController;
import org.mabufudyne.designer.gui.WindowSubController;
import sun.applet.Main;

import java.util.concurrent.Semaphore;

class ApplicationInitTest {

    /**
     * Creates a new JFXPanel, which launches the FX environment/toolkit.
     * We need this in order to instantiate and work with JFX controls in tests
     */
    public static void setUpFX() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @Tag("slow")
    @Test
    void Main_ShouldInitializeTheApplicationGUISuccessfully() {
        /*
         * Run main with test argument, this will cause the main window to be hidden immediately, exiting the application
         * If there are no exceptions during this process, the application was launched successfully
         */
        String[] args = {"--testRun=true"};
        ApplicationInit.main(args);
    }

    @Test
    void Init_ShouldInitializeTheApplicationState() throws Exception {
        ApplicationInit appInit = new ApplicationInit();
        appInit.init();

        assertNotNull(appInit.getInitializedApplication(),
                "ApplicationInit did not initialize an Application instance");
        assertNotNull(appInit.getInitializedApplication().getActiveAdventure(),
                "The initialized Application does not have an active Adventure");
        assertNotNull(appInit.getInitializedApplication().getActiveAdventure().getStoryPieces(),
                "The Adventure in the initialized Application does not have a StoryPiece");
        assertNotNull(appInit.getInitializedApplication().getProperties(),
                "ApplicationInit did not initialise (default) properties");
    }

    @Test
    void ControllerFactory_ShouldPopulateMainControllerMap_GivenMainControllerFirstAndSubControllersSecond() {
        ApplicationInit appInit = new ApplicationInit();
        MainWindowController mwc = (MainWindowController) appInit.controllerFactory.call(MainWindowController.class);
        StoryPieceViewController spvc = (StoryPieceViewController) appInit.controllerFactory.call(StoryPieceViewController.class);

        assertNotNull(mwc, "MainWindowController instance was not created");
        assertEquals(spvc, mwc.getController("StoryPieceViewController"),
                "MainWindowController controller map was not set up correctly");
    }

    @Tag("slow")
    @Test
    void ControllerFactory_ShouldPrintErrorAndQuitTheProgram_GivenAnExceptionOccurs() throws Throwable {
        ApplicationInit appInit = new ApplicationInit();
        setUpFX();
        final Throwable[] error = {null};

        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.show();
            // Pass abstract class to cause an exception
            appInit.controllerFactory.call(WindowSubController.class);

            // Use the final array to capture the potential test fail and pass it to the main thread
            try {
                if (stage.isShowing()) fail("Stage was not closed by controller factory after encountering an exception");
            } catch (Throwable e) {
                error[0] = e;
            }
        });

        // Wait for JavaFX thread started by Plaftorm.runLater()
        Thread.sleep(1000);
        if (error[0] != null) throw error[0];
    }
}
