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
import services.exceptions.PseudoDejaPrisException;
import services.exceptions.PseudoOuMotDePasseIncorrectException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

public class VueInscription implements Vue{

    /* Les noms des attributs doivent être les noms des fx:id */
    @FXML
    private AnchorPane baseAnchor;

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

    public static VueInscription creerVue(Stage stage) throws IOException
    {
        URL location = VueInscription.class.getResource("inscription.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VueInscription vue = fxmlLoader.getController();
        vue.setStage(stage);
        Scene scene = new Scene(root);
        vue.setScene(scene);
        vue.initBoutonRetour();
        vue.initBoutonValider();
        return vue;
    }

    private void initBoutonValider() {
        this.buttonValider.setOnAction(e -> {
            try {
                inscription();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (NotBoundException ex) {
                ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (PseudoDejaPrisException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pseudo déjà pris !", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }

    private void initBoutonRetour() {
        this.buttonRetour.setOnAction(e -> {
            try {
                goMenu();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            } catch (NotBoundException notBoundException) {
                notBoundException.printStackTrace();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
        });
    }

    private void inscription() throws RemoteException, NotBoundException, MalformedURLException, PseudoDejaPrisException {
        String pseudoInscr = this.textFieldPseudo.getText();
        String mdpInscr = this.textFieldMDP.getText();

        if(pseudoInscr.length() >= 5 || mdpInscr.length() >= 5) {
            if (this.controleur.inscription(pseudoInscr, mdpInscr)) {
                System.out.println("Inscription de " + pseudoInscr);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vous êtes inscrits " + pseudoInscr + " ! Veuillez vous connecter.", ButtonType.OK);
                alert.showAndWait();
                this.controleur.goToConnexion();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "erreur d'inscription...", ButtonType.OK);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Chaque champ doit comporter au minimum 6 caractères !", ButtonType.OK);
            alert.showAndWait();
        }
    }
    private void goMenu() throws RemoteException, NotBoundException, MalformedURLException {
        this.controleur.goToMenu();
    }

    @Override
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setX(0);
        this.stage.setY(0);
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
