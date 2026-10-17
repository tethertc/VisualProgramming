package lab7;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EditorController {

    @FXML private TextArea txtEditor;
    @FXML private Label lblStatus;
    private Path currentFile;

    @FXML
    public void initialize() {
        // Счётчик символов
        txtEditor.textProperty().addListener((obs, oldText, newText) -> {
            lblStatus.setText("Символов: " + newText.length());
        });

        // Контекстное меню (правая кнопка мыши)
        ContextMenu contextMenu = new ContextMenu();
        MenuItem cutItem = new MenuItem("Вырезать");
        cutItem.setOnAction(e -> onCut());
        MenuItem copyItem = new MenuItem("Копировать");
        copyItem.setOnAction(e -> onCopy());
        MenuItem pasteItem = new MenuItem("Вставить");
        pasteItem.setOnAction(e -> onPaste());

        contextMenu.getItems().addAll(cutItem, copyItem, pasteItem);
        txtEditor.setContextMenu(contextMenu);
    }

    @FXML
    private void onNew() {
        txtEditor.clear();
        currentFile = null;
        lblStatus.setText("Новый документ");
    }

    @FXML
    private void onOpen() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
        File file = chooser.showOpenDialog(txtEditor.getScene().getWindow());
        if (file == null) return;
        try {
            txtEditor.setText(Files.readString(file.toPath(), StandardCharsets.UTF_8));
            currentFile = file.toPath();
            lblStatus.setText("Открыт: " + file.getName());
        } catch (IOException ex) {
            showError("Ошибка открытия: " + ex.getMessage());
        }
    }

    @FXML
    private void onSave() {
        if (currentFile == null) {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));
            File file = chooser.showSaveDialog(txtEditor.getScene().getWindow());
            if (file == null) return;
            currentFile = file.toPath();
        }
        try {
            Files.writeString(currentFile, txtEditor.getText(), StandardCharsets.UTF_8);
            lblStatus.setText("Сохранено: " + currentFile.getFileName());
        } catch (IOException ex) {
            showError("Ошибка сохранения: " + ex.getMessage());
        }
    }

    @FXML private void onCut() { txtEditor.cut(); }
    @FXML private void onCopy() { txtEditor.copy(); }
    @FXML private void onPaste() { txtEditor.paste(); }

    @FXML
    private void onExit() {
        Platform.exit();
    }

    @FXML
    private void onAbout() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("О программе");
        a.setHeaderText("Редактор заметок. Лабораторная работа №7.");
        a.getButtonTypes().setAll(ButtonType.OK);
        a.showAndWait();
    }

    private void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Ошибка");
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}