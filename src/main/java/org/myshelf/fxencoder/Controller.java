package org.myshelf.fxencoder;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;

public class Controller {

    private static final Function<Integer, Image> ERROR_IMAGE = (size) -> {
        Text t = new Text("ERROR");
        new Scene(new StackPane(t));
        return t.snapshot(null, null);
    };

    public Text text;
    public ImageView ivCode;
    public BorderPane root;

    private final double font_size = 20.0;
    private double size = 800;

    /**
     * Initializes the controller with the params from the main application.
     */
    public void init(double height) {
        if (height > 0) {
            this.size = height;

            this.root.setMinHeight(this.size);
            this.root.setMaxHeight(this.size);
            this.reprint();
        }
    }

    /**
     * Initializes the ui components and their listeners.
     */
    @FXML
    public void initialize() {
        // Font Size
        this.text.fontProperty().setValue(new Font(font_size));

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);
        String toEncrypt = Base64.getEncoder().encodeToString(bytes);
        this.setCode(toEncrypt);
    }

    private void reprint() {
        String code = this.text.getText();
        this.generateCode(code);
    }

    private void setCode(@NotNull String input) {
        // Generate code and show
        String code = BCrypt.hashpw(input, BCrypt.gensalt(12));
        this.generateCode(code);
    }

    private void generateCode(String code) {
        QRCodeEncoder encoder = new QRCodeEncoder((int) this.size);
        Optional<BufferedImage> optImage = encoder.getCodeImage(code);
        if (optImage.isPresent()) {
            // Set Code to Image
            this.ivCode.setImage(SwingFXUtils.toFXImage(optImage.get(), null));
            // Set Code Text
        } else {
            this.ivCode.setImage(ERROR_IMAGE.apply((int) this.size));
        }
        this.text.textProperty().setValue(code);
    }
}
