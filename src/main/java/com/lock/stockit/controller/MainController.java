package com.lock.stockit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private void initialize() {
        loadView("home.fxml"); // Default content
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
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lock/stockit/" + fxmlFile)));
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleSingOut(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lock/stockit/sign-in.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
