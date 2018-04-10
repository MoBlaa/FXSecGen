package org.myshelf.fxencoder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.myshelf.fxencoder.components.Footer;
import org.myshelf.fxencoder.components.Header;

import java.io.File;

public class SecondStepController {
    @FXML
    private Button btnOpenFiles;
    @FXML
    private Footer footer;
    @FXML
    private Header header;

    void init(Stage primaryStage,
              EventHandler<ActionEvent> onNext,
              EventHandler<ActionEvent> onPrevious) {
        // Footer-Navigation Handler
        this.footer.setOnNextAction(onNext);
        this.footer.setOnPreviousAction(onPrevious);

        final FileChooser fileChooser = new FileChooser();

        // Open File-Dialog
        this.btnOpenFiles.setOnAction((event -> {
            configureFileChooser(fileChooser);
            File chosen = fileChooser.showOpenDialog(primaryStage);
            if (chosen != null) {
                this.processFile(chosen);
            }
        }));
    }

    private void processFile(@NotNull File file) {
        //TODO
    }

    private static void configureFileChooser(@NotNull final FileChooser fileChooser) {
        fileChooser.setTitle("Open PublicKey-File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Public-Key Files", ".pub")
        );
    }
}
