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

public class VueMenuConnecte implements Vue{

    @FXML
    Label pseudo;

    @FXML
    ListView listAmis;

    @FXML
    Button boutonQuitter;

    private Stage stage;
    private Controleur controleur;

    private void setScene(Scene scene) {
        this.scene = scene;
    }

    private Scene scene;

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public static VueMenuConnecte creer(Stage stage) {
        URL location = VueMenuConnecte.class.getResource("menuConnecte.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VueMenuConnecte vue = fxmlLoader.getController();
        vue.setStage(stage);
        Scene scene = new Scene(root);
        vue.setScene(scene);
        vue.initialiserBoutonQuitter();
        return vue;
    }

    private void initialiserBoutonQuitter() { this.boutonQuitter.setOnAction(e -> goExit()); }

    private void goExit() {
        this.controleur.exit();
    }

    @Override
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setX(0);
        this.stage.setY(0);
        this.stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR,message,ButtonType.OK);
        alert.setTitle("Connexion");
        alert.showAndWait();
    }

    @Override
    public void chargerDonnees() {
        String nom = controleur.getNom();
        this.pseudo.setText(nom);
        List<User> amis = controleur.getAmis();
        this.listAmis.getItems().clear();
        for(User a : amis){
            this.listAmis.getItems().add(a.getPseudo());
        }
    }

    @Override
    public void initialiserControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void addFriend(ActionEvent actionEvent) {
        TextInputDialog ajoutAmi = new TextInputDialog();
        ajoutAmi.setTitle("Ajouter un ami inscrit");
        ajoutAmi.setHeaderText("Entrez le pseudo de l'ami");

        Optional<String> resultat = ajoutAmi.showAndWait();

        if (resultat.isPresent()){
            try {
                this.controleur.addFriend(this.controleur.getUser(), resultat.get());
                this.listAmis.getItems().add(resultat.get());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            this.showError("Vous devez saisir un pseudo pour ajouter un ami");
        }
    }

}
