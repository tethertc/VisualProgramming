package travelplanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {
    @FXML private TextField txtDest;
    @FXML private TextField txtBudget;

    @FXML
    public void onSave() {
        try {
            String dest = txtDest.getText();
            double budget = Double.parseDouble(txtBudget.getText());
            MainController.data.add(new Trip(dest, budget));
            ((Stage) txtDest.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Ошибка ввода!");
        }
    }
}