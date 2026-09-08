module com.example.studentcardapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studentcardapp to javafx.fxml;
    exports com.example.studentcardapp;
}