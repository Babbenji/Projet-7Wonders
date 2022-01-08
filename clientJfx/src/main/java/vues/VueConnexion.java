package vues;

import controleur.Controleur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class VueConnexion implements Vue{

    /* Les noms des attributs doivent être les noms des fx:id */
    @FXML
    private AnchorPane baseAnchor;
    @FXML
    private ImageView imageFond;
    @FXML
    private ImageView imageLogo;
    @FXML
    private VBox vbChamps;
    @FXML
    private TextField textFieldPseudo;
    @FXML
    private PasswordField textFieldMDP;
    @FXML
    private Button buttonRetour;
    @FXML
    private Button buttonValider;

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

    public static VueConnexion creerVue(Stage stage) throws IOException
    {
        URL location = VueConnexion.class.getResource("connexion.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VueConnexion vue = fxmlLoader.getController();
        vue.setStage(stage);
        Scene scene = new Scene(root);
        vue.setScene(scene);
        vue.initBoutonValider();
        vue.initBoutonRetour();
        return vue;
    }

    private void initBoutonValider() {
        this.buttonValider.setOnAction(e -> connexion());
    }

    private void initBoutonRetour() {
        this.buttonRetour.setOnAction(e -> goMenu());
    }

    private void connexion() {
        String pseudo = this.textFieldPseudo.getText();
        if (Objects.isNull(pseudo) || pseudo.length()<2) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Vous devez renseigner un pseudo d'au moins 2 caractères !!", ButtonType.OK);
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"OK 👌", ButtonType.OK);
            alert.showAndWait();
            //controleur.connexion(pseudoJoueur);
        }
    }
    private void goMenu() {
        this.controleur.goToMenu();
    }

    @Override
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    @Override
    public void chargerDonnees() {

    }

    @Override
    public void initialiserControleur(Controleur controleur) {
        this.controleur = controleur;
    }
}
