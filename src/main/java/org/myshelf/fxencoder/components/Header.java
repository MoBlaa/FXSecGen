package org.myshelf.fxencoder.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Header extends BorderPane {
    @FXML private Label lbTitle;

    private StringProperty textProperty;

    public Header() {
        this.textProperty = new SimpleStringProperty();

        FXMLLoader fxmlLoader = new FXMLLoader(Footer.class.getResource("Header.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        this.lbTitle.textProperty().bindBidirectional(this.textProperty);
    }

    public void setText(String text) {
        this.textProperty.setValue(text);
    }

    public StringProperty getTextProperty() {
        return this.textProperty;
    }

    public String getText() {
        return this.textProperty.get();
    }
}
