package com.lock.stockit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-in.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("StockIt - Sign In");
            primaryStage.setScene(new Scene(root));
            primaryStage.setWidth(250);
            primaryStage.setHeight(180);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
