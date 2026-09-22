package kz.atu.lab04;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LayoutApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                LayoutApplication.class.getResource("/kz/atu/lab04/layout-view.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        stage.setTitle("Лаба №4 — Компоновка JavaFX");
        stage.setScene(scene);
        stage.setMinWidth(760);
        stage.setMinHeight(520);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
