package org.mabufudyne.designer.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApplicationInit extends javafx.application.Application {

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
        if (getParameters().getNamed().get("testRun").equals("true")) {
            primaryStage.hide();
        }

    }

    @Override
    public void init() {
        Application app = Application.getApp();
        app.initialize();
    }

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }
}
