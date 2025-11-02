package org.novaria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homePage.fxml"));
        Parent root = loader.load();

        // Set up the scene and stage
        primaryStage.setTitle("Novaria 📚");
        primaryStage.setScene(new Scene(root, 800, 600));  // width x height
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
