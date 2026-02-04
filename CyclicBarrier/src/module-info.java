module toy.language.interpreter {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;

    opens view;
    opens model.utilities.DTOs;
}
