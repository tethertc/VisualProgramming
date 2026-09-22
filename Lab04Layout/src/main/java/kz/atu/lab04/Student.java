package kz.atu.lab04;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty fullName = new SimpleStringProperty("");
    private final StringProperty group = new SimpleStringProperty("");
    private final StringProperty course = new SimpleStringProperty("");

    public Student(String f, String g, String c) {
        fullName.set(f);
        group.set(g);
        course.set(c);
    }

    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty groupProperty() { return group; }
    public StringProperty courseProperty() { return course; }
}
