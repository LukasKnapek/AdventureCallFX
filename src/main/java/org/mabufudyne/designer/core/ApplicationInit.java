package org.mabufudyne.designer.core;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.mabufudyne.designer.gui.MainWindowController;
import org.mabufudyne.designer.gui.OverviewController;
import org.mabufudyne.designer.gui.WindowSubController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ApplicationInit extends javafx.application.Application {

    private Application initializedApplication;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));

        // Collect all instantiated subcontrollers in a single data structure rather than inject them
        // into separate fields of MainWindowController
        HashMap<String, WindowSubController> controllerMap = new HashMap<>();
        loader.setControllerFactory(param -> {
            Object instance = null;

            try {
                instance = param.newInstance();

                if (!(instance instanceof MainWindowController)) {
                    WindowSubController instantiatedController = (WindowSubController) instance;
                    controllerMap.put(instantiatedController.getClass().getSimpleName(), instantiatedController);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return instance;
        });
        VBox mainWindow = loader.load();

        Scene mainScene = new Scene(mainWindow);
        primaryStage.setTitle("Welcome to Adventure Call FX!");
        primaryStage.setScene(mainScene);

        // Set the stage dimensions to the dimensions of the root element of the scene
        primaryStage.setMinWidth(mainWindow.getMinWidth());
        primaryStage.setMinHeight(mainWindow.getMinHeight());

        primaryStage.show();

        MainWindowController mc = loader.getController();
        mc.setControllers(controllerMap);
        setUpControllers(mc);

        // If we pass testing argument to the application, hide the stage immediately, exiting the app
        // This is used to test that the app launches successfully
        String testParamValue = getParameters().getNamed().get("testRun");
        if (testParamValue != null && testParamValue.equals("true")) {
            Platform.setImplicitExit(true);
            primaryStage.close();
        }
    }

    private void setUpControllers(MainWindowController mc) {
        for (WindowSubController controller : mc.getControllers().values()) {
            controller.setApp(initializedApplication);
            controller.setMainController(mc);

            controller.populateControls();
            controller.setupListeners();
        }

        // Select the first StoryPiece to populate StoryPiece View and Choice View
        OverviewController oc = (OverviewController) mc.getController("OverviewController");
        oc.getStoryPiecesTable().getSelectionModel().select(0);
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
