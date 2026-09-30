package kz.atu.lab03;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML private TextField txtNumber1;
    @FXML private TextField txtNumber2;
    @FXML private Label lblResult;
    @FXML private Label lblOperation;
    @FXML private Label lblCounter;

    private int operationCount = 0;

    @FXML
    private void onOperation(ActionEvent event) {
        if (txtNumber1.getText().isBlank() || txtNumber2.getText().isBlank()) {
            showError("Введите оба числа.");
            return;
        }

        double number1;
        double number2;
        try {
            number1 = Double.parseDouble(txtNumber1.getText().trim());
            number2 = Double.parseDouble(txtNumber2.getText().trim());
        } catch (NumberFormatException e) {
            showError("Введите корректные числа.");
            return;
        }

        Button button = (Button) event.getSource();
        String operation = button.getText();

        double result;
        String operationName;

        switch (operation) {
            case "+":
                result = number1 + number2;
                operationName = "Операция: сложение";
                break;
            case "−":
            case "-":
                result = number1 - number2;
                operationName = "Операция: вычитание";
                break;
            case "×":
            case "*":
                result = number1 * number2;
                operationName = "Операция: умножение";
                break;
            case "÷":
            case "/":
                if (number2 == 0) {
                    showError("Деление на ноль невозможно.");
                    return;
                }
                result = number1 / number2;
                operationName = "Операция: деление";
                break;
            case "xʸ":
                result = Math.pow(number1, number2);
                operationName = "Операция: возведение в степень";
                break;
            case "Ост.":
                if (number2 == 0) {
                    showError("Остаток от деления на ноль невозможен.");
                    return;
                }
                result = number1 % number2;
                operationName = "Операция: остаток от деления";
                break;
            default:
                return;
        }

        operationCount++;
        lblResult.setText(String.format("Результат: %.2f", result));
        lblOperation.setText(operationName);
        lblCounter.setText("Выполнено операций: " + operationCount);
    }

    @FXML
    private void onClearClick() {
        txtNumber1.clear();
        txtNumber2.clear();
        lblResult.setText("Результат: 0");
        lblOperation.setText("Операция: —");
        txtNumber1.requestFocus();
    }

    @FXML
    private void onExitClick() {
        Platform.exit();
    }

    @FXML
    private void onLockClick() {
        txtNumber1.setDisable(true);
        txtNumber2.setDisable(true);
    }

    @FXML
    private void onUnlockClick() {
        txtNumber1.setDisable(false);
        txtNumber2.setDisable(false);
        txtNumber1.requestFocus();
    }

    @FXML
    private void onMouseEntered() {
        lblOperation.setText("Выберите операцию");
    }

    @FXML
    private void onMouseExited() {
        if (lblResult.getText().equals("Результат: 0")) {
            lblOperation.setText("Операция: —");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}