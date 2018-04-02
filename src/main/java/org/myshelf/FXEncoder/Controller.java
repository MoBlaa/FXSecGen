package org.myshelf.FXEncoder;

import com.google.zxing.BarcodeFormat;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.image.BufferedImage;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

public class Controller {

    private static final Function<Integer, Image> ERROR_IMAGE = (size) -> {
        Text t = new Text("ERROR");
        new Scene(new StackPane(t));
        return t.snapshot(null, null);
    };
    private static final int MAX_BCRYPT_INPUT_BYTES = 72;

    public TextField tfInput;
    public TextField tfResult;
    public ImageView ivCode;

    private final int qrCodeSize = 300;

    @FXML
    public void initialize() {
        // Set maximum characters (ensures the maximum of 50 characters is not exceeded
        this.tfInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (this.tfInput.getText().length() > MAX_BCRYPT_INPUT_BYTES) {
                String s = tfInput.getText().substring(0, MAX_BCRYPT_INPUT_BYTES);
                tfInput.setText(s);
            }
        }));

        // Font Size
        this.tfResult.fontProperty().setValue(new Font(10.0));

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

    public void onCodeChoiceChanged(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                String input = this.tfInput.textProperty().get();
                this.setCode(input);
                break;
        }
    }
}
