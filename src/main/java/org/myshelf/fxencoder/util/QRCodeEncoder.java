package org.myshelf.fxencoder.util;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Optional;

public class QRCodeEncoder {
    private final Writer writer;
    private final int size;

    public QRCodeEncoder(int size) {
        this.size = size;
        this.writer = new MultiFormatWriter();
    }

    public Optional<BufferedImage> getCodeImage(@NotNull String input) {
        EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BufferedImage image = null;
        try {
            BitMatrix matrix = this.writer.encode(input, BarcodeFormat.QR_CODE, this.size, this.size, hints);
            image = new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, this.size, this.size);
            graphics.setColor(Color.BLACK);

            for (int i=0; i < this.size; i++) {
                for (int j=0; j < this.size; j++) {
                    if (matrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(image);
    }
}
