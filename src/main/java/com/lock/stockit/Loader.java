package com.lock.stockit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.Objects;

public class Loader extends Application {

    @Override
    public void start(Stage splashStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loader-ui.fxml")));
            Scene scene = new Scene(root, 250, 180);
            splashStage.initStyle(StageStyle.UNDECORATED);
            splashStage.setScene(scene);
            splashStage.centerOnScreen();
            splashStage.show();

            // Delay before showing login
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                splashStage.close();
                new Login().start(new Stage());
            });
            pause.play();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
