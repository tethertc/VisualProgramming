package kz.atu.lab01;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CardController {

    @FXML private Label lblFullName;
    @FXML private Label lblGroup;
    @FXML private Label lblProgram;
    @FXML private Button btnExit;

    @FXML
    private void onExitClick() {
        Platform.exit();
    }
}