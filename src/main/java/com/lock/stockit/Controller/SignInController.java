package com.lock.stockit.Controller;

import com.google.gson.JsonObject;
import com.lock.stockit.Helper.FirebaseAuthService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter email and password.");
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
                            // Load Main.fxml after successful login
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lock/stockit/main.fxml"));
                            Parent root = loader.load();

                            Stage stage = new Stage();
                            stage.setTitle("StockIt");
                            stage.setScene(new Scene(root));
                            stage.setMaximized(true);
                            stage.setResizable(false);
                            stage.show();

                            // Close login window
                            Stage currentStage = (Stage) signInButton.getScene().getWindow();
                            currentStage.close();

                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    });
                } else {
                    Platform.runLater(() -> {
                        errorLabel.setText("Invalid email or password.");
                        progressIndicator.setVisible(false);
                        errorLabel.setVisible(true);
                        signInButton.setDisable(false);
                    });
                }
            } catch (IOException e) {
                Platform.runLater(() -> {
                    errorLabel.setText("Login failed. Please check your connection.");
                    progressIndicator.setVisible(false);
                    errorLabel.setVisible(true);
                    signInButton.setDisable(false);
                });
            }
        }).start();
    }
}
