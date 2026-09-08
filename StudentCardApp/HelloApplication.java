package com.example.studentcard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Загружаем FXML файл с интерфейсом
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("hello-view.fxml")
        );

        // Создаем сцену
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);

        // Настраиваем окно
        stage.setTitle("Карточка студента");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}