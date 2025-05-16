package com.lock.stockit.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button menu, home, receipt, inventory, more;

    @FXML
    private VBox slider;

    @FXML
    private AnchorPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadView("home.fxml");
        menu.setOnAction(event -> handleMenu());
        home.setOnAction(event -> handleHome());
        receipt.setOnAction(event -> handleReceipt());
        inventory.setOnAction(event -> handleInventory());
        more.setOnAction(event -> handleMore());
    }

    @FXML
    private void handleMenu() {
//        TranslateTransition slide = new TranslateTransition();
//        slide.setDuration(javafx.util.Duration.seconds(0.4));
//        slide.setNode(slider);
//
//        int x = 0, y = -100;
//        if (slider.getTranslateX() == x) {
//            x = y;
//            y = 0;
//        }
//        slide.setToX(x);
//        slide.play();
//
//        slider.setTranslateX(y);

        boolean isExpanded = slider.getPrefWidth() == 200;
        double targetWidth = isExpanded ? 60 : 200;

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
        loadView("home.fxml");
    }

    @FXML
    private void handleReceipt() {
        loadView("receipt.fxml");
    }

    @FXML
    private void handleInventory() {
        loadView("inventory.fxml");
    }

    @FXML
    private void handleMore() {
        loadView("more.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lock/stockit/layout/" + fxmlFile)));
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

//    @FXML
//    private void handleSingOut(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lock/stockit/sign-in.fxml"));
//            Parent root = loader.load();
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.centerOnScreen();
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
}
