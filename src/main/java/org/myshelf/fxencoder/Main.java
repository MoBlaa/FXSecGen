package org.myshelf.fxencoder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.myshelf.fxencoder.util.KeyExchangeHelper;

import java.io.IOException;
import java.security.PublicKey;
import java.security.Security;

public class Main extends Application {

    private int screenHeight;
    private KeyExchangeHelper helper;
    private Stage primaryStage;

    private int currentStep;

    public Main() {
        this.helper = new KeyExchangeHelper();
        this.currentStep = 0;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Get the preferred size of the Code-Window
        Rectangle2D primary = Screen.getPrimary().getVisualBounds();
        this.screenHeight = (int) (primary.getHeight() / 3);

        primaryStage.setTitle("Pairing");

        this.onNext();

        primaryStage.show();
    }

    private void onNext() {
        Parent newRoot = null;
        FXMLLoader loader;

        try {
            switch (currentStep) {
                case 0:
                    // Switch to first Step
                    loader = new FXMLLoader(FirstStepController.class.getResource("FirstStep.fxml"));
                    newRoot = loader.load();

                    // Initialize first Step-UI
                    PublicKey pubKey = this.helper.getPublicKey();

                    FirstStepController controller = loader.getController();
                    controller.init(pubKey,
                            (event) -> this.onNext(),
                            (event) -> controller.update(this.helper.generateKeypair().getPublicKey()));
                    break;
                case 1:
                    // Switch to second State
                    loader = new FXMLLoader(SecondStepController.class.getResource("SecondStep.fxml"));
                    newRoot = loader.load();

                    // Initialize second Step-UI
                    SecondStepController secController = loader.getController();
                    secController.init(primaryStage,
                            (event) -> this.onNext(),
                            (event) -> this.onPrevious());
                    break;
                case 2:

                    break;
            }
        }catch (IOException e) {
            // Shouldn't be a problem as of simple Resource-Loading
            e.printStackTrace();
        }
        assert newRoot != null;
        this.primaryStage.setScene(new Scene(newRoot));
        this.currentStep++;
    }

    private void onPrevious() {

    }


    public static void main(String[] args) {
        // Adding the BouncyCastle Provider
        Security.addProvider(new BouncyCastleProvider());

        launch(args);
    }
}
