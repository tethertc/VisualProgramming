package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("/questionnaire-view.fxml")
        );
        Scene scene = new Scene(loader.load(), 520, 660);
        stage.setTitle("Анкета студента");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}