package kz.atu.lab08;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MainController {

    @FXML private TextArea txtNotes;
    @FXML private Label lblStatus;

    private Color currentColor = Color.BLACK;

    @FXML
    private void onChooseColor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("color-dialog.fxml"));
        Parent root = loader.load();

        ColorDialogController controller = loader.getController();
        controller.setInitialColor(currentColor);

        Stage dialog = new Stage();
        dialog.initOwner(txtNotes.getScene().getWindow());
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Цвет текста");
        dialog.setScene(new Scene(root));
        dialog.showAndWait();

        controller.getSelectedColor().ifPresent(color -> {
            currentColor = color;
            txtNotes.setStyle("-fx-text-fill: " + toCss(color) + ";");
            lblStatus.setText("Цвет текста изменён");
        });
    }

    private String toCss(Color c) {
        return String.format("#%02X%02X%02X",
                Math.round(c.getRed() * 255),
                Math.round(c.getGreen() * 255),
                Math.round(c.getBlue() * 255));
    }

    @FXML
    private void onOpen() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Открыть заметку");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
        File file = chooser.showOpenDialog(txtNotes.getScene().getWindow());
        if (file == null) return;

        try {
            txtNotes.setText(Files.readString(file.toPath(), StandardCharsets.UTF_8));
            lblStatus.setText("Открыт: " + file.getName());
        } catch (IOException ex) {
            showError(ex.getMessage());
        }
    }

    @FXML
    private void onSaveAs() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Сохранить заметку");
        chooser.setInitialFileName("note.txt");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
        File file = chooser.showSaveDialog(txtNotes.getScene().getWindow());
        if (file == null) return;

        try {
            Files.writeString(file.toPath(), txtNotes.getText(), StandardCharsets.UTF_8);
            lblStatus.setText("Сохранён: " + file.getName());
        } catch (IOException ex) {
            showError(ex.getMessage());
        }
    }

    @FXML
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Редактор заметок");
        alert.setContentText("Лабораторная работа №8\nРазработчик: Студент");
        alert.showAndWait();
    }

    @FXML
    private void onExit() {
        Stage stage = (Stage) txtNotes.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Ошибка ввода-вывода");
        a.setHeaderText("Не удалось выполнить операцию");
        a.setContentText(message);
        a.showAndWait();
    }
}
