package org.mabufudyne.designer.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationInit extends javafx.application.Application {

    private static final Logger LOGGER = Logger.getLogger( ApplicationInit.class.getName() );
    private Application app;

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox mainWindow = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        Scene mainScene = new Scene(mainWindow);

        primaryStage.setTitle("Welcome to Adventure Call FX!");
        primaryStage.setScene(mainScene);

        // Set the stage dimensions to the dimensions of the root element of the scene
        primaryStage.setMinWidth(mainWindow.getMinWidth());
        primaryStage.setMinHeight(mainWindow.getMinHeight());

        primaryStage.show();

        // If we pass testing argument to the application, hide the stage immediately, exiting the app
        // This is used to test that the app launches successfully
        String testParamValue = getParameters().getNamed().get("testRun");
        if (testParamValue != null && testParamValue.equals("true")) {
            primaryStage.hide();
        }
    }

    public void setApp(Application newApp) {
        app = newApp;
    }

    @Override
    public void init() throws Exception{
        if (app == null)
            app = Application.getApp();

        try {
            app.initialize();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while initializing application:");
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw e;
        }
    }

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }
}
