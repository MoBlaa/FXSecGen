package org.myshelf.fxencoder;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.bouncycastle.util.encoders.Base64;
import org.myshelf.fxencoder.components.Footer;
import org.myshelf.fxencoder.components.Header;
import org.myshelf.fxencoder.util.QRCodeEncoder;

import java.awt.image.BufferedImage;
import java.security.PublicKey;
import java.util.Optional;
import java.util.function.Function;

public class FirstStepController {

    private static final Function<Integer, Image> ERROR_IMAGE = (size) -> {
        Text t = new Text("ERROR");
        new Scene(new StackPane(t));
        return t.snapshot(null, null);
    };

    @FXML
    private ImageView ivCode;
    @FXML
    private Footer footer;
    @FXML
    private Header header;

    private double size = 800;

    void init(PublicKey code,
              EventHandler<ActionEvent> onNext,
              EventHandler<ActionEvent> onRefresh) {
        this.update(code);

        this.footer.setOnNextAction(onNext);
        this.footer.setOnRefreshAction(onRefresh);
    }

    /**
     * Initializes the controller with the params from the main application.
     */
    void update(PublicKey code) {
        String encoded = Base64.toBase64String(code.getEncoded());
        this.toQRCode(encoded);
    }

    private void toQRCode(String code) {
        QRCodeEncoder encoder = new QRCodeEncoder((int) this.size);
        Optional<BufferedImage> optImage = encoder.getCodeImage(code);
        if (optImage.isPresent()) {
            // Set Code to Image
            this.ivCode.setImage(SwingFXUtils.toFXImage(optImage.get(), null));
            // Set Code Text
        } else {
            this.ivCode.setImage(ERROR_IMAGE.apply((int) this.size));
        }
    }
}
