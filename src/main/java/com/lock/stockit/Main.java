package com.lock.stockit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label welcome = new Label("Welcome to StockIt PC!");
        StackPane root = new StackPane(welcome);
        root.setStyle("-fx-padding: 40px;");

        Scene scene = new Scene(root);

        primaryStage.setTitle("StockIt");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}