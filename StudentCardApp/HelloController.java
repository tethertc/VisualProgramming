package com.example.studentcard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    // ===== ПОЛЯ ИЗ FXML =====
    @FXML private TextField txtName;
    @FXML private TextField txtSurname;
    @FXML private TextField txtAge;
    @FXML private TextField txtSpeciality;
    @FXML private ComboBox<String> cmbCourse;
    @FXML private Label lblResult;

    // ===== САМОСТОЯТЕЛЬНАЯ ЧАСТЬ =====
    @FXML private ComboBox<String> cmbCity;
    @FXML private RadioButton rbFullTime;
    @FXML private RadioButton rbDistance;
    @FXML private ToggleGroup tgStudyMode;
    @FXML private CheckBox chkDormitory;

    // ===== ИНИЦИАЛИЗАЦИЯ (заполняем ComboBox) =====
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Заполняем курс
        cmbCourse.getItems().addAll("1 курс", "2 курс", "3 курс", "4 курс");
        cmbCourse.getSelectionModel().selectFirst();

        // Заполняем город (самостоятельная часть)
        cmbCity.getItems().addAll("Москва", "Санкт-Петербург", "Казань",
                "Новосибирск", "Екатеринбург");
        cmbCity.getSelectionModel().selectFirst();

        // Связываем RadioButton с ToggleGroup
        tgStudyMode = new ToggleGroup();
        rbFullTime.setToggleGroup(tgStudyMode);
        rbDistance.setToggleGroup(tgStudyMode);
        rbFullTime.setSelected(true); // По умолчанию "Очная"
    }

    // ===== ОБРАБОТЧИК "СФОРМИРОВАТЬ" =====
    @FXML
    private void onCreateClick() {
        // Получаем данные из полей
        String name = txtName.getText().trim();
        String surname = txtSurname.getText().trim();
        String ageText = txtAge.getText().trim();
        String speciality = txtSpeciality.getText().trim();
        String course = cmbCourse.getValue();
        String city = cmbCity.getValue();

        // Проверка на пустые поля
        if (name.isEmpty() || surname.isEmpty() || ageText.isEmpty() ||
                speciality.isEmpty()) {
            showError("Заполните все поля!");
            return;
        }

        // Проверка возраста
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            showError("Возраст должен быть числом!");
            return;
        }

        if (age < 16 || age > 100) {
            showError("Возраст должен быть от 16 до 100 лет!");
            return;
        }

        // Получаем форму обучения
        String studyMode = rbFullTime.isSelected() ? "Очная" : "Дистанционная";

        // Получаем статус общежития
        String dormitory = chkDormitory.isSelected() ? "Да" : "Нет";

        // Формируем результат
        String result = "Студент: " + surname + " " + name + "\n" +
                "Возраст: " + age + "\n" +
                "Специальность: " + speciality + "\n" +
                "Курс: " + course + "\n" +
                "Город: " + city + "\n" +
                "Форма обучения: " + studyMode + "\n" +
                "Проживает в общежитии: " + dormitory;

        lblResult.setText(result);
    }

    // ===== ОБРАБОТЧИК "ОЧИСТИТЬ" =====
    @FXML
    private void onClearClick() {
        txtName.clear();
        txtSurname.clear();
        txtAge.clear();
        txtSpeciality.clear();
        cmbCourse.getSelectionModel().selectFirst();
        cmbCity.getSelectionModel().selectFirst();
        rbFullTime.setSelected(true);
        chkDormitory.setSelected(false);
        lblResult.setText("");
        txtName.requestFocus();
    }

    // ===== ОБРАБОТЧИК "ВЫХОД" =====
    @FXML
    private void onExitClick() {
        // Закрываем приложение
        System.exit(0);
    }

    // ===== ВСПОМОГАТЕЛЬНЫЙ МЕТОД ДЛЯ ОШИБОК =====
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}