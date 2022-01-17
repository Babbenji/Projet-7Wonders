package vues;

import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VueScores implements Vue{

    @FXML
    ListView listJoueurs;

    @FXML
    ListView listScores;

    @FXML
    Button boutonQuitter;

    @FXML
    Button boutonRejouer;

    private Stage stage;
    private Controleur controleur;

    private void setScene(Scene scene) {
        this.scene = scene;
    }

    private Scene scene;

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public static VueScores creer(Stage stage) {
        URL location = VueScores.class.getResource("scores.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        VueScores vue = fxmlLoader.getController();
        vue.setStage(stage);
        Scene scene = new Scene(root);
        vue.setScene(scene);
        vue.initialiserBoutonQuitter();
        /*vue.initialiserBoutonRejouer();*/
        return vue;
    }

    void initialiserBoutonQuitter() { this.boutonQuitter.setOnAction(e -> goExit()); }

    /*private void initialiserBoutonRejouer() { this.boutonRejouer.setOnAction(e -> goToWaitingRoom()); }*/

    private void goExit() {
        this.controleur.exit();
    }

    /*private void goToWaitingRoom() {
        this.controleur.goToWaitingRoom();
    }*/

    @Override
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setX(0);
        this.stage.setY(0);
        this.stage.show();
    }

    @Override
    public void chargerDonnees() {
        /*List<Joueur> joueurs = controleur.getPartie.getJoueurs();
        this.listJoueurs.getItems().clear();
        /*for(Joueur j : joueurs){
            this.listJoueurs.getItems().add(j.getNom());
            //this.listScores.getItems().add();    ici l'ajout du score du joueur concerné
        */
        }



    @Override
    public void initialiserControleur(Controleur controleur) {
        this.controleur = controleur;
    }

}
