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
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class VueConnexion implements Vue{

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
        this.buttonValider.setOnAction(e -> {
            try {
                connexion();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            } catch (NotBoundException notBoundException) {
                notBoundException.printStackTrace();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            } catch (PseudoOuMotDePasseIncorrectException pseudoOuMotDePasseIncorrectException) {
                pseudoOuMotDePasseIncorrectException.printStackTrace();
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

    private void connexion() throws RemoteException, NotBoundException, MalformedURLException, PseudoOuMotDePasseIncorrectException {
        String pseudo = this.textFieldPseudo.getText();
        String mdp = this.textFieldMDP.getText();

        if(this.controleur.connexion(pseudo,mdp)){
            System.out.println("Connexion de " + pseudo);
            this.controleur.goToMenuConnecte();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Mauvais identifiants 😢", ButtonType.OK);
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
