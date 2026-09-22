package kz.atu.lab04;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LayoutController {

    @FXML private VBox navigationBox;
    @FXML private TextField txtFullName;
    @FXML private TextField txtGroup;
    @FXML private ComboBox<String> cmbCourse;
    @FXML private ComboBox<String> cmbStudyForm;
    @FXML private TextArea txtNote;
    @FXML private TableView<Student> tblStudents;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colGroup;
    @FXML private TableColumn<Student, String> colCourse;
    @FXML private Label lblStatus;

    private final ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cmbCourse.getItems().addAll("1 курс", "2 курс", "3 курс", "4 курс");
        cmbCourse.getSelectionModel().selectFirst();

        cmbStudyForm.getItems().addAll("Очная", "Заочная", "Дистанционная");
        cmbStudyForm.getSelectionModel().selectFirst();

        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGroup.setCellValueFactory(new PropertyValueFactory<>("group"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));

        tblStudents.setItems(students);
        tblStudents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void onSaveClick() {
        String n = txtFullName.getText().trim();
        String g = txtGroup.getText().trim();
        String c = cmbCourse.getValue();

        if (n.isBlank() || g.isBlank()) {
            lblStatus.setText("Статус: заполните ФИО и группу");
            return;
        }
        students.add(new Student(n, g, c));
        lblStatus.setText("Статус: сохранено — " + n + ", " + g + ", " + c);
    }

    @FXML
    private void onClearClick() {
        txtFullName.clear();
        txtGroup.clear();
        txtNote.clear();
        cmbCourse.getSelectionModel().selectFirst();
        cmbStudyForm.getSelectionModel().selectFirst();
        lblStatus.setText("Статус: форма очищена");
        txtFullName.requestFocus();
    }

    @FXML
    private void onHideNavClick() {
        boolean v = navigationBox.isVisible();
        navigationBox.setVisible(!v);
        navigationBox.setManaged(!v);
        lblStatus.setText(v ? "Статус: навигация скрыта" : "Статус: навигация показана");
    }

    @FXML
    private void onAboutClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/kz/atu/lab04/about-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle("О программе");
        dlg.setScene(scene);
        dlg.showAndWait();
    }
}
