package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class QuestionnaireController {

    @FXML private TextField txtFullName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtGroup;
    @FXML private DatePicker dpBirthDate;
    @FXML private ComboBox<String> cmbCity;

    @FXML private RadioButton rbCourse1;
    @FXML private RadioButton rbCourse2;
    @FXML private RadioButton rbCourse3;
    @FXML private RadioButton rbCourse4;
    @FXML private ToggleGroup courseGroup;

    @FXML private RadioButton rbFullTime;
    @FXML private RadioButton rbOnline;
    @FXML private ToggleGroup studyFormGroup;

    @FXML private CheckBox chkDormitory;
    @FXML private CheckBox chkScholarship;
    @FXML private CheckBox chkActivist;
    @FXML private CheckBox chkConsent;

    @FXML private Button btnCreate;
    @FXML private Button btnClear;
    @FXML private Button btnExit;

    @FXML private Label lblResult;

    @FXML
    public void initialize() {
        cmbCity.getItems().addAll(
                "Алматы", "Астана", "Шымкент", "Караганда", "Атырау"
        );
        btnCreate.setDisable(true);
        txtFullName.textProperty().addListener((obs, oldV, newV) ->
                btnCreate.setDisable(newV == null || newV.trim().isEmpty())
        );
    }

    @FXML
    private void onCreateClick() {
        String fullName = txtFullName.getText().trim();
        String email    = txtEmail.getText().trim();
        String group    = txtGroup.getText().trim();

        if (fullName.isEmpty() || email.isEmpty() || group.isEmpty()) {
            showError("Заполните обязательные поля: ФИО, Email, Группа.");
            return;
        }

        if (!isEmailValid(email)) {
            showError("Некорректный формат Email. Пример: ivanov@example.com");
            txtEmail.requestFocus();
            return;
        }

        RadioButton course = (RadioButton) courseGroup.getSelectedToggle();
        if (course == null) {
            showError("Выберите курс обучения.");
            return;
        }

        RadioButton studyForm = (RadioButton) studyFormGroup.getSelectedToggle();
        if (studyForm == null) {
            showError("Выберите форму обучения.");
            return;
        }

        if (!chkConsent.isSelected()) {
            showError("Необходимо согласие на обработку данных.");
            return;
        }

        String extras = buildExtras();
        String city = cmbCity.getValue() == null ? "не указан" : cmbCity.getValue();
        String birthDate = dpBirthDate.getValue() == null
                ? "не указана"
                : dpBirthDate.getValue().toString();

        String card =
                "ФИО: " + fullName + "\n" +
                        "Email: " + email + "\n" +
                        "Группа: " + group + "\n" +
                        "Город: " + city + "\n" +
                        "Дата рождения: " + birthDate + "\n" +
                        "Курс: " + course.getText() + "\n" +
                        "Форма обучения: " + studyForm.getText() + "\n" +
                        "Дополнительно: " + extras;

        lblResult.setText(card);
    }

    @FXML
    private void onClearClick() {
        txtFullName.clear();
        txtEmail.clear();
        txtGroup.clear();
        dpBirthDate.setValue(null);
        cmbCity.setValue(null);
        courseGroup.selectToggle(null);
        studyFormGroup.selectToggle(null);
        chkDormitory.setSelected(false);
        chkScholarship.setSelected(false);
        chkActivist.setSelected(false);
        chkConsent.setSelected(false);
        lblResult.setText("Результат:");
        txtFullName.requestFocus();
    }

    @FXML
    private void onExitClick() {
        Platform.exit();
    }

    private String buildExtras() {
        StringBuilder sb = new StringBuilder();
        if (chkDormitory.isSelected())   sb.append("общежитие; ");
        if (chkScholarship.isSelected()) sb.append("стипендия; ");
        if (chkActivist.isSelected())    sb.append("активист; ");
        if (sb.length() == 0) return "не выбрано";
        return sb.toString().trim();
    }

    private boolean isEmailValid(String email) {
        int at  = email.indexOf("@");
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1;
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Ошибка ввода");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}