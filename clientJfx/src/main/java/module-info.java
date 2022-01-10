module clientJfx {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.rmi;
    requires plateau;
    requires model;
    requires dao;
    requires rmiService;
    opens vues to javafx.fxml;
    exports application;

}