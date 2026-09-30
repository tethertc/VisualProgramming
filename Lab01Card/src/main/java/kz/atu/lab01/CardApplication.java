package kz.atu.lab01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CardApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                CardApplication.class.getResource("/kz/atu/lab01/card-view.fxml")
        );
        Scene scene = new Scene(loader.load(), 480, 400);
        stage.setTitle("Визитная карточка студента");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}