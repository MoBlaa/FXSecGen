package org.myshelf.FXEncoder;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.Function;

public class Controller {

    private static final Function<Integer, Image> ERROR_IMAGE = (size) -> {
        Text t = new Text("ERROR");
        new Scene(new StackPane(t));
        return t.snapshot(null, null);
    };
    private static final int MAX_BCRYPT_INPUT_BYTES = 72;

    public TextField tfResult;
    public ImageView ivCode;

    private final int qrCodeSize = 800;
    private final double font_size = 20.0;

    @FXML
    public void initialize() {
        // Font Size
        this.tfResult.fontProperty().setValue(new Font(font_size));

        // Resize to text size
        this.tfResult.textProperty().addListener(((observable, oldValue, newValue) -> {
            double prefWidth = qrCodeSize;
            double text_length = tfResult.getText().length() * font_size / 2;
            if (text_length > prefWidth) {
                prefWidth = text_length;
            }
            tfResult.setPrefWidth(prefWidth);
        }));

        String toEncrypt = "https://www.google.com";
        this.setCode(toEncrypt);
    }

    private void setCode(@NotNull String input) {
        // Generate code and show
        String code = BCrypt.hashpw(input, BCrypt.gensalt(12));

        QRCodeEncoder encoder = new QRCodeEncoder(qrCodeSize);
        Optional<BufferedImage> optImage = encoder.getCodeImage(code);
        if (optImage.isPresent()) {
            // Set Code to Image
            this.ivCode.setImage(SwingFXUtils.toFXImage(optImage.get(), null));
            // Set Code Text
        } else {
            this.ivCode.setImage(ERROR_IMAGE.apply(qrCodeSize));
        }
        this.tfResult.textProperty().setValue(code);
    }
}
