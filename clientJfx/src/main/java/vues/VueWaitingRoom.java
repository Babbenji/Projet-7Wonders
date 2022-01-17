package vues;

import controleur.Controleur;
import exceptions.MaxJoueursAtteintException;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import joueur.Joueur;
import partie.Partie;
import services.exceptions.JoueurDejaAjouteException;
import services.exceptions.JoueurNonExistantException;
import type.IJoueur;
import type.IPartie;
import user.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VueWaitingRoom implements Vue{

    @FXML
    ListView listAmis;

    @FXML
    ListView listJoueurs;

   @FXML
    Button boutonRetour;

    private Stage stage;
    private Controleur controleur;
    private List<Partie>parties;
    private IPartie partie;
    private int nombre;

    private void setScene(Scene scene) {
        this.scene = scene;
    }

    private Scene scene;

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public static VueWaitingRoom creerVue(Stage stage) throws RemoteException {
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
        vue.initBoutonRetour();

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
            this.parties = this.controleur.getFacade().getParties();
            for(Partie p : this.parties){
                for(IJoueur j : p.getListeDesJoueurs()){
                    if (j.getNom().equals(this.controleur.getUser().getPseudo())){
                        this.partie = p;
                    }
                }
            }
            List<IJoueur> j = this.controleur.getFacade().getPartieById(this.controleur.getFacade().getPartieById(this.partie.getIdPartie()).getIdPartie()).getListeDesJoueurs();
            this.listJoueurs.getItems().clear();
            for (IJoueur joueur : j){
                this.listJoueurs.getItems().add(joueur.getNom());
            }



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
            int idPartie = this.controleur.getPartie().getIdPartie();
            this.controleur.inviterUser(idPartie, user);
            List<IJoueur> j = this.controleur.getFacade().getPartieById(idPartie).getListeDesJoueurs();
            this.listJoueurs.getItems().clear();
            for (IJoueur joueur : j){
                this.listJoueurs.getItems().add(joueur.getNom());
            }
        }
    }
    private void initBoutonRetour() throws RemoteException {
            this.boutonRetour.setOnAction(e -> {
            this.controleur.goToMenuConnecte();

        });
    }

    public void goToPartie(ActionEvent actionEvent) {
        Task<Boolean> attenteJoueur = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                while (controleur.getNbUserWaitingRoom() != 4);
                return true;
            }
        };
        attenteJoueur.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> {
            try {
                controleur.goToPartie();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });
        Thread thread = new Thread(attenteJoueur);
        thread.start();
    }
}
