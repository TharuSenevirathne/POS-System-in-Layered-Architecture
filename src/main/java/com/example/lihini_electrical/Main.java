package com.example.lihini_electrical;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/LoadingScreen.fxml"))));
        stage.show();

        Task<Scene> loadingTask = new Task() {
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Image.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            stage.setTitle("TS ELECTRICAL ( PVT ) LTD");
            stage.setMaximized(true);

            stage.setScene(value);
        });

        loadingTask.setOnFailed(event -> {
            System.err.println("Failed to load image.");
        });

        new Thread(loadingTask).start();
    }
}