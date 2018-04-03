package org.myshelf.fxencoder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Get the preferred size of the Code-Window
        Rectangle2D primary = Screen.getPrimary().getVisualBounds();
        int screenHeight = (int) primary.getHeight() / 4;

        // Get the FXMLLoader to init the fxml and controllers
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("sample.fxml")
        );

        Parent root = loader.load();

        primaryStage.setTitle("Code-Generator");
        primaryStage.setScene(new Scene(root));

        // Load controller and init
        Controller controller = loader.getController();
        controller.init(screenHeight);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
