package kz.atu.lab12.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kz.atu.lab12.model.Student;
import kz.atu.lab12.repository.StudentRepository;

import java.sql.SQLException;
import java.util.Optional;

public class StudentController {

    @FXML private TableView<Student> tableStudents;
    @FXML private TableColumn<Student, Number> colId;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colGroup;
    @FXML private TableColumn<Student, Number> colGpa;

    @FXML private TextField txtName;
    @FXML private TextField txtGroup;
    @FXML private TextField txtGpa;
    @FXML private TextField txtSearch;
    @FXML private Label lblStatus;

    private final StudentRepository repository = new StudentRepository();
    private final ObservableList<Student> items = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().id()));
        colName.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().fullName()));
        colGroup.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().groupName()));
        colGpa.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().gpa()));

        FilteredList<Student> filtered = new FilteredList<>(items, s -> true);
        tableStudents.setItems(filtered);

        txtSearch.textProperty().addListener((obs, oldV, newV) -> {
            String q = newV == null ? "" : newV.trim().toLowerCase();
            filtered.setPredicate(s -> s.fullName().toLowerCase().contains(q));
        });

        try {
            repository.initializeDatabase();
            refresh();
        } catch (SQLException e) {
            showError("Не удалось открыть базу данных");
        }
    }

    private void refresh() throws SQLException {
        items.setAll(repository.findAll());
        lblStatus.setText("Записей: " + items.size());
    }

    @FXML
    private void onAdd() {
        try {
            double gpa = readGpa();
            repository.insert(txtName.getText().trim(), txtGroup.getText().trim(), gpa);
            clearFields();
            refresh();
        } catch (IllegalArgumentException | SQLException e) {
            showError(e instanceof SQLException ? "Ошибка сохранения в БД" : e.getMessage());
        }
    }

    @FXML
    private void onUpdate() {
        Student selected = tableStudents.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Выберите запись для изменения");
            return;
        }
        try {
            double gpa = readGpa();
            repository.update(selected.id(), txtName.getText().trim(), txtGroup.getText().trim(), gpa);
            refresh();
        } catch (IllegalArgumentException | SQLException e) {
            showError(e instanceof SQLException ? "Ошибка обновления БД" : e.getMessage());
        }
    }

    @FXML
    private void onDelete() {
        Student selected = tableStudents.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Выберите запись для удаления");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Подтверждение удаления");
        confirm.setHeaderText(null);
        confirm.setContentText("Удалить студента \"" + selected.fullName() + "\"?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                repository.delete(selected.id());
                refresh();
            } catch (SQLException e) {
                showError("Ошибка удаления из БД");
            }
        }
    }

    @FXML
    private void onRefresh() {
        try { refresh(); } catch (SQLException e) { showError("Ошибка загрузки данных"); }
    }

    private double readGpa() {
        String name = txtName.getText().trim();
        String group = txtGroup.getText().trim();
        if (name.isBlank() || group.isBlank()) throw new IllegalArgumentException("Заполните ФИО и группу");

        double gpa;
        try { gpa = Double.parseDouble(txtGpa.getText().trim()); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("GPA должен быть числом"); }

        if (!Double.isFinite(gpa) || gpa < 0 || gpa > 4) throw new IllegalArgumentException("GPA должен быть от 0 до 4");
        return gpa;
    }

    private void clearFields() {
        txtName.clear();
        txtGroup.clear();
        txtGpa.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}