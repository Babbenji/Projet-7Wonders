package vues;

import controleur.Controleur;
import exceptions.MaxJoueursAtteintException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.exceptions.JoueurDejaAjouteException;
import services.exceptions.JoueurNonExistantException;
import type.IJoueur;
import type.IPartie;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VueWaitingRoom implements Vue{

    @FXML
    ListView listAmis;

    @FXML
    ListView listJoueurs;

    private Stage stage;
    private Controleur controleur;
    private IPartie partie;

    private void setScene(Scene scene) {
        this.scene = scene;
    }

    private Scene scene;

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public static VueWaitingRoom creerVue(Stage stage) {
        URL location = VueWaitingRoom.class.getResource("waitingRoom.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VueWaitingRoom vue = fxmlLoader.getController();
        vue.setStage(stage);
        Scene scene = new Scene(root);
        vue.setScene(scene);
        return vue;
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
    public void chargerDonnees() {
        try {
            this.partie = this.controleur.getFacade().creePartie(this.controleur.getUser());
            this.listJoueurs.getItems().add(this.controleur.getUser().getPseudo());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    @FXML
    public void onButtonInvit(MouseEvent arg) throws JoueurDejaAjouteException, MaxJoueursAtteintException, JoueurNonExistantException, RemoteException {
        if(this.listJoueurs.getItems().size()<4){
            System.out.println("invitation de "+listAmis.getSelectionModel().getSelectedItem().toString()+" à la partie");
            User user = this.controleur.getFacade().getUserByPseudo(listAmis.getSelectionModel().getSelectedItem().toString());
            System.out.println(user);
            int idPartie = this.controleur.getFacade().getPartieById(this.partie.getIdPartie()).getIdPartie();
            this.controleur.inviterUser(idPartie, user);
            System.out.println("partie dans la vue : "+this.partie+" "+this.partie.getListeDesJoueurs());
            System.out.println("partie côté serveur : "+this.controleur.getFacade().getPartieById(this.partie.getIdPartie())+this.controleur.getFacade().getPartieById(this.partie.getIdPartie()).getListeDesJoueurs());
            this.listJoueurs.getItems().add(user.getPseudo());
        }
    }

}
