package org.mabufudyne.designer.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationInit extends javafx.application.Application {

    private Application initializedApplication;

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

    @Override
    public void init() throws Exception {
        String defaultPropertiesPath = "config.properties";
        Properties defaultProps = loadDefaultProperties(defaultPropertiesPath);

        Application app = new Application(defaultProps);
        Adventure initialAdventure = new Adventure(app, new StoryPiece());
        app.setActiveAdventure(initialAdventure);

        setInitializedApplication(app);
    }

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }

    private Properties loadDefaultProperties(String path) throws IOException {
        Properties props = new Properties();

        FileInputStream in = new FileInputStream(path);
        props.load(in);
        return props;
    }

    Application getInitializedApplication() {
        return initializedApplication;
    }

    private void setInitializedApplication(Application initializedApplication) {
        this.initializedApplication = initializedApplication;
    }

}
