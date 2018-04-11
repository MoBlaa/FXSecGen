package org.myshelf.fxencoder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.myshelf.fxencoder.cipher.DefaultAlice;
import org.myshelf.fxencoder.cipher.IAlice;

import java.io.IOException;
import java.security.PublicKey;
import java.security.Security;

public class Main extends Application {

    private IAlice helper;
    private Stage primaryStage;

    private int currentStep;

    public Main() {
        this.helper = new DefaultAlice();
        this.currentStep = 0;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Pairing");
        this.onNext(false);
        primaryStage.show();
    }

    private void onNext(boolean reversed) {
        Parent newRoot;

        if (reversed) {
            newRoot = this.previous();
            this.currentStep--;
        } else {
            newRoot = this.next();
            this.currentStep++;
        }
        assert newRoot != null;
        this.primaryStage.setScene(new Scene(newRoot));
    }

    private Parent previous() {
        try {
            switch (this.currentStep) {
                case 2:
                    return loadFirstStep();
                case 3:
                    return loadSecondStep();
            }
        } catch (IOException e) {
            // Shouldn't be a problem as of simple Resource-Loading
            e.printStackTrace();
        }
        return null;
    }

    private Parent next() {
        try {
            switch (this.currentStep) {
                case 0:
                    return loadFirstStep();
                case 1:
                    return loadSecondStep();
            }
        } catch (IOException e) {
            // Shouldn't be a problem as of simple Resource-Loading
            e.printStackTrace();
        }
        return null;
    }

    private Parent loadFirstStep() throws IOException {
        // Switch to first Step
        var loader = new FXMLLoader(FirstStepController.class.getResource("FirstStep.fxml"));
        Parent newRoot = loader.load();

        // Initialize first Step-UI
        FirstStepController controller = loader.getController();
        controller.init(this.helper.generatePublicKey(),
                (event) -> this.onNext(false),
                (event) -> controller.update(this.helper.generatePublicKey()));

        return newRoot;
    }

    private Parent loadSecondStep() throws IOException {
        // Switch to second State
        var loader = new FXMLLoader(SecondStepController.class.getResource("SecondStep.fxml"));
        Parent newRoot = loader.load();

        // Initialize second Step-UI
        SecondStepController secController = loader.getController();
        secController.init(primaryStage,
                (event) -> this.onNext(false),
                (event) -> this.onNext(true));

        return newRoot;
    }


    public static void main(String[] args) {
        // Adding the BouncyCastle Provider
        Security.addProvider(new BouncyCastleProvider());

        launch(args);
    }
}
