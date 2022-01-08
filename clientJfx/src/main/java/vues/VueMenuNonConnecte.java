package vues;

import controleur.Controleur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class VueMenuNonConnecte implements Vue{

    /* Les noms des attributs doivent être les noms des fx:id */
    @FXML
    private AnchorPane baseAnchor;
    @FXML
    private ImageView imageFond;
    @FXML
    private VBox vboxMenu;
    @FXML
    private Button buttonConnexion;
    @FXML
    private Button buttonInscription;
    @FXML
    private Button buttonQuitter;

    private Scene scene;
    private Controleur controleur;
    private Stage stage;


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initialiserVue() {
        this.scene = new Scene(baseAnchor, 1920, 1080);
    }

    public static VueMenuNonConnecte creerVue(Stage stage) throws IOException
    {
        URL location = VueMenuNonConnecte.class.getResource("menu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VueMenuNonConnecte vue = fxmlLoader.getController();
        vue.setStage(stage);
        Scene scene = new Scene(root);
        vue.setScene(scene);
        vue.initBoutonConnexion();
        vue.initBoutonInscription();
        vue.initBoutonQuitter();
        return vue;
    }

    private void initBoutonConnexion() {
        this.buttonConnexion.setOnAction(e -> goConnexion());
    }

    private void initBoutonInscription() {
        this.buttonInscription.setOnAction(e -> goInscription());
    }

    private void initBoutonQuitter() {
        this.buttonQuitter.setOnAction(e -> goExit());
    }

    private void goConnexion() {
        this.controleur.goToConnexion();
    }

    private void goInscription() {
        this.controleur.goToInscription();
    }

    private void goExit() {
        this.controleur.exit();
    }

    @Override
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    @Override
    public void chargerDonnees(){}

    @Override
    public void initialiserControleur(Controleur controleur)
    {
        this.controleur = controleur;
    }
}