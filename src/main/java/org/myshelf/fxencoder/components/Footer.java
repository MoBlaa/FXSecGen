package org.myshelf.fxencoder.components;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Footer extends BorderPane {

    private static EventHandler<ActionEvent> getProxy(@NotNull ObjectProperty<EventHandler<ActionEvent>> property) {
        return (event) -> {
            if (property.isNotNull().get()) {
                property.get().handle(event);
            }
        };
    }

    @FXML private Button btnPrevious;
    @FXML private Button btnNext;
    @FXML private Button btnRefresh;

    private ObjectProperty<EventHandler<ActionEvent>> propertyOnRefreshAction;
    private ObjectProperty<EventHandler<ActionEvent>> propertyOnNextAction;
    private ObjectProperty<EventHandler<ActionEvent>> propertyOnPreviousAction;

    public Footer() {
        this.propertyOnNextAction = new SimpleObjectProperty<>();
        this.propertyOnPreviousAction = new SimpleObjectProperty<>();
        this.propertyOnRefreshAction = new SimpleObjectProperty<>();

        FXMLLoader fxmlLoader = new FXMLLoader(Footer.class.getResource("Footer.fxml"));
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
        btnNext.setOnAction(getProxy(onNextActionProperty()));
        btnPrevious.setOnAction(getProxy(onPreviousActionProperty()));
        btnRefresh.setOnAction(getProxy(onRefreshActionProperty()));

        Platform.runLater(btnNext::requestFocus);
    }

    // ========== Refresh Action =========

    public EventHandler<ActionEvent> getOnRefreshAction() {
        return propertyOnRefreshAction.get();
    }

    public ObjectProperty<EventHandler<ActionEvent>> onRefreshActionProperty() {
        return propertyOnRefreshAction;
    }

    public void setOnRefreshAction(EventHandler<ActionEvent> propertyOnRefreshAction) {
        this.propertyOnRefreshAction.set(propertyOnRefreshAction);
    }

    // =========== Next Action ==========

    public EventHandler<ActionEvent> getOnNextAction() {
        return propertyOnNextAction.get();
    }

    public ObjectProperty<EventHandler<ActionEvent>> onNextActionProperty() {
        return propertyOnNextAction;
    }

    public void setOnNextAction(EventHandler<ActionEvent> propertyOnNextAction) {
        this.propertyOnNextAction.set(propertyOnNextAction);
    }

    // ========== Previous Action =========

    public EventHandler<ActionEvent> getOnPreviousAction() {
        return propertyOnPreviousAction.get();
    }

    public ObjectProperty<EventHandler<ActionEvent>> onPreviousActionProperty() {
        return propertyOnPreviousAction;
    }

    public void setOnPreviousAction(EventHandler<ActionEvent> propertyOnPreviousAction) {
        this.propertyOnPreviousAction.set(propertyOnPreviousAction);
    }

    // ========== Can Refresh ==========

    public Boolean getCanRefresh() {
        return !this.btnRefresh.disableProperty().get();
    }

    public BooleanProperty canRefreshProperty() {
        return this.btnRefresh.disableProperty();
    }

    public void setCanRefresh(Boolean value) {
        this.btnRefresh.disableProperty().set(!value);
    }

    // ========== Has Previous Step ==========

    public Boolean getHasPrevious() {
        return !this.btnPrevious.disableProperty().get();
    }

    public BooleanProperty hasPreviousProperty() {
        return this.btnPrevious.disableProperty();
    }

    public void setHasPrevious(Boolean value) {
        this.btnPrevious.disableProperty().set(!value);
    }

    // ========== Has Next Step ==========

    public Boolean getHasNext() {
        return !this.btnNext.disableProperty().get();
    }

    public BooleanProperty hasNextProperty() {
        return this.btnNext.disableProperty();
    }

    public void setHasNext(Boolean value) {
        this.btnNext.disableProperty().set(!value);
    }
}
