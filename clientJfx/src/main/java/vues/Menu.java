package vues;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;

import java.io.IOException;

public class Menu {

    /* Les noms des attributs doivent être les noms des fx:id */
    @FXML
    private AnchorPane baseAnchor;

    @FXML
    private ImageView imageFond;

    @FXML
    private VBox vboxMenu;

    private Scene scene;

    private void initialiserVue() {
        this.scene = new Scene(baseAnchor, 1920, 1080);
    }

    public static Menu creerVue() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("Menu.fxml"));

        Parent root = fxmlLoader.load();
        Menu resultat = fxmlLoader.getController();
        resultat.initialiserVue();
        return resultat;
    }

    public Scene getScene() {
        return scene;
    }
}