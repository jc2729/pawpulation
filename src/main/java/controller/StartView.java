package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public class StartView extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        final Parent root = loader.load();
        ((LoginController) loader.getController()).setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pawpulation");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
