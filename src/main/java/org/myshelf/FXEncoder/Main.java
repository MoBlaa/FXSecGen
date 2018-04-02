package org.myshelf.FXEncoder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(new URL("file:////home/moblaa/Dokumente/workspace/myshelf/CodeGenerator/src/main/java/org/myshelf/FXEncoder/sample.fxml"));
        primaryStage.setTitle("Code-Generator");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
