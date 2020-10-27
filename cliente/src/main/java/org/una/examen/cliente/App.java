package org.una.examen.cliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;
import org.una.examen.cliente.controller.util.FlowController;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().goViewInNoResizableWindow("Principal", Boolean.TRUE, StageStyle.DECORATED);
    }

    public static void main(String[] args) {
        launch();
    }

}