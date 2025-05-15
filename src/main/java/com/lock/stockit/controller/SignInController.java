package com.lock.stockit.controller;

import com.google.gson.JsonObject;
import com.lock.stockit.helper.FirebaseAuthService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        signInButton.setOnAction(event -> handleSignIn());
    }

    @FXML
    private void handleSignIn() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();


        if (email.isEmpty()) {
            errorLabel.setText("Please enter email.");
            errorLabel.setVisible(true);
            return;
        }

        if (password.isEmpty()) {
            errorLabel.setText("Please enter password.");
            errorLabel.setVisible(true);
            return;
        }

        progressIndicator.setVisible(true);
        errorLabel.setVisible(false);

        signInButton.setDisable(true);

        new Thread(() -> {
            try {
                JsonObject result = FirebaseAuthService.signIn(email, password);
                if (result != null) {
                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lock/stockit/main.fxml"));
                            Parent root = loader.load();

                            Stage stage = new Stage();
                            stage.setTitle("StockIt");
                            stage.setScene(new Scene(root));
                            stage.setFullScreen(true);
                            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                            stage.show();

                            Stage currentStage = (Stage) signInButton.getScene().getWindow();
                            currentStage.close();

                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    });
                } else {
                    Platform.runLater(() -> {
                        progressIndicator.setVisible(false);
                        errorLabel.setVisible(true);
                        signInButton.setDisable(false);
                    });
                }
            } catch (Exception exception) {
                Platform.runLater(() -> {
                    String errorMessage = switch (exception.getMessage()) {
                        case "INVALID_LOGIN_CREDENTIALS" -> "Incorrect email or password.\nPlease try again.";
                        case "INVALID_EMAIL" -> "Invalid email address.\nPlease enter a valid email address.";
                        default -> exception.getMessage();
                    };
                    errorLabel.setText(errorMessage);
                    progressIndicator.setVisible(false);
                    errorLabel.setVisible(true);
                    signInButton.setDisable(false);
                });
            }
        }).start();
    }
}
