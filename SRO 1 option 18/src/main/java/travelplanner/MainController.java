package travelplanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    @FXML private TableView<Trip> table;
    @FXML private TableColumn<Trip, String> colDest;
    @FXML private TableColumn<Trip, Double> colBudget;

    public static ObservableList<Trip> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colDest.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colBudget.setCellValueFactory(new PropertyValueFactory<>("budget"));

        // Добавляем 10 демо-записей для галочки
        if (data.isEmpty()) {
            data.add(new Trip("Алматы", 50000));
            data.add(new Trip("Астана", 80000));
            data.add(new Trip("Москва", 150000));
            data.add(new Trip("Дубай", 400000));
            data.add(new Trip("Анталья", 250000));
            data.add(new Trip("Стамбул", 180000));
            data.add(new Trip("Бишкек", 40000));
            data.add(new Trip("Ташкент", 60000));
            data.add(new Trip("Бали", 600000));
            data.add(new Trip("Париж", 800000));
        }
        table.setItems(data);
    }

    @FXML
    public void onAddClick() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/travelplanner/edit.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Добавить");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}