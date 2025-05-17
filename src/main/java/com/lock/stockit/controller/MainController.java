package com.lock.stockit.controller;

import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Button activeButton = null;

    @FXML
    private Button menu, home, receipt, inventory, more;

    @FXML
    private VBox slider;

    @FXML
    private AnchorPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadView("home.fxml", home);
        menu.setOnAction(event -> handleMenu());
        home.setOnAction(event -> handleHome());
        receipt.setOnAction(event -> handleReceipt());
        inventory.setOnAction(event -> handleInventory());
        more.setOnAction(event -> handleMore());
    }

    @FXML
    private void handleMenu() {
        boolean isExpanded = slider.getPrefWidth() == 143;
        double targetWidth = isExpanded ? 0 : 143;

        // Animate the width
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(slider.prefWidthProperty(), targetWidth, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // Toggle button texts after the animation
        timeline.setOnFinished(event -> {
            menu.setText(isExpanded ? "" : "Menu");
            home.setText(isExpanded ? "" : "Home");
            receipt.setText(isExpanded ? "" : "Receipt");
            inventory.setText(isExpanded ? "" : "Inventory");
            more.setText(isExpanded ? "" : "More");
        });
    }

    @FXML
    private void handleHome() {
        loadView("home.fxml", home);
    }

    @FXML
    private void handleReceipt() {
        loadView("receipt.fxml", receipt);
    }

    @FXML
    private void handleInventory() {
        loadView("inventory.fxml", inventory);
    }

    @FXML
    private void handleMore() {
        loadView("more.fxml", more);
    }

    private void loadView(String fxmlFile, Button button) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lock/stockit/layout/" + fxmlFile)));
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            contentArea.getChildren().setAll(node);
            highlightActiveButton(button);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void highlightActiveButton(Button newButton) {
        String defaultStyle = "-fx-background-color: transparent;";

        // If there's a previously active button, fade out its highlight
        if (activeButton != null && activeButton != newButton)
            animateBackgroundColor(activeButton, Color.rgb(255, 255, 255, 0.2), Color.TRANSPARENT, () -> activeButton.setStyle(defaultStyle));

        // Fade in highlight on the newly active button
        animateBackgroundColor(newButton, Color.TRANSPARENT, Color.rgb(255, 255, 255, 0.2), null);

        activeButton = newButton;
    }

    private void animateBackgroundColor(Button button, Color from, Color to, Runnable onFinished) {
        ObjectProperty<Color> color = new SimpleObjectProperty<>(from);
        color.addListener((obs, oldVal, newVal) -> {
            String style = String.format("-fx-background-color: rgba(%d, %d, %d, %.2f);",
                    (int)(newVal.getRed() * 255),
                    (int)(newVal.getGreen() * 255),
                    (int)(newVal.getBlue() * 255),
                    newVal.getOpacity());
            button.setStyle(style);
        });

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(color, from)),
                new KeyFrame(Duration.millis(200), new KeyValue(color, to))
        );

        if (onFinished != null) timeline.setOnFinished(e -> onFinished.run());

        timeline.play();
    }

}
