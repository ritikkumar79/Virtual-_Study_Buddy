package com.studybuddy.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator {

    public static void switchScene(Stage stage, String fxml, String css) {

        try {

            // Save stage state
            boolean isMaximized = stage.isMaximized();
            boolean isFullScreen = stage.isFullScreen();
            double width = stage.getWidth();
            double height = stage.getHeight();

            FXMLLoader loader = new FXMLLoader(
                    Navigator.class.getResource("/fxml/" + fxml));

            Parent root = loader.load();

            Scene scene = new Scene(root);

            if (css != null) {
                scene.getStylesheets().add(
                        Navigator.class.getResource("/css/" + css).toExternalForm()
                );
            }

            stage.setScene(scene);

            // Restore stage state
            if (isFullScreen) {
                stage.setFullScreen(true);
            } else if (isMaximized) {
                stage.setMaximized(true);
            } else {
                stage.setWidth(width);
                stage.setHeight(height);
            }

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}