package org.mabufudyne.designer.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.mabufudyne.designer.gui.MainWindowController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationInit extends javafx.application.Application {

    private Application initializedApplication;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        VBox mainWindow = loader.load();
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

        setUpControllers(loader.getController());
    }

    private void setUpControllers(MainWindowController mc) {
        // TODO: Suboptimal way of calling functions on subcontrollers
        // Find a way to gather them in a data structure that would make the code more clean and elegant
        mc.getOverviewController().setApp(initializedApplication);
        mc.getStoryPieceViewController().setApp(initializedApplication);

        mc.getOverviewController().setMainController(mc);
        mc.getStoryPieceViewController().setMainController(mc);

        mc.getOverviewController().setUpControls();
        mc.getStoryPieceViewController().setUpControls();
    }

    @Override
    public void init() throws Exception {
        String defaultPropertiesPath = "config.properties";
        Properties defaultProps = loadDefaultProperties(defaultPropertiesPath);

        // Create a new Application and populate it with a new Adventure
        Application app = new Application(defaultProps);
        new Adventure(app, new StoryPiece());

        setInitializedApplication(app);
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

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }

}
