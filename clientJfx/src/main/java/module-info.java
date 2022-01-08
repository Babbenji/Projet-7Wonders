module clientJfx {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.rmi;
    requires interfaces;
    requires model;
    opens vues to javafx.fxml;
    exports application;

}