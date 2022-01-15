package vues;

import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import type.ICarte;
import type.IDeck;
import type.IJoueur;
import type.IMerveille;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VuePartie implements Vue
{

    @FXML
    public Button boutonEtape;
    @FXML
    public Button boutonJouer;
    @FXML
    public Button boutonDefausser;
    @FXML
    public ListView lv;

    public Label lab;
    public ImageView merveilleVoisinGauche;
    public Button jGauche;
    public ImageView merveilleJoueurFace;
    public Button jFace;
    public ImageView merveilleVoisinDroite;
    public Button jDroite;

    public Label argent;
    public Label pvm;
    public Label bouclier;
    public Label pdm;
    public Label pv;
    public Label rouages;
    public Label compas;
    public Label tabelettes;

    @FXML
    private ImageView merveilleIM;

    private ImageView imageSelectionne;
    

    private Scene scene;
    private Controleur controleur;
    private Stage stage;
    private ICarte carte;
    Map<ImageView,ICarte> associationCarteImageview;


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static VuePartie creerVue(Stage stage, Controleur controleur) throws RemoteException {
        URL location = VuePartie.class.getResource("partie.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        VuePartie vue = fxmlLoader.getController();
        vue.setStage(stage);
        Scene scene = new Scene(root);
        vue.setScene(scene);
        vue.setControleur(controleur);
        vue.initialiserCarteMerveille();
        return vue;
    }

    @Override
    public void initialiserControleur(Controleur controleur)
    {
        this.controleur = controleur;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void show()
    {
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void initialiserCarteMerveille() throws RemoteException { // a mettre dans le charge donne quand les tests seront finis
        this.controleur.miseEnPlaceDuJeu();
        IMerveille merveille = this.controleur.getMerveille();
        File file = new File("clientJfx/src/main/resources/images/");
        Image image = new Image(file.toURI().toString()+merveille.getImage());
        merveilleIM.setImage(image);
        affichageInteractifDesVariables();


    }
    public void affichageInteractifDesVariables() throws RemoteException {
        IJoueur joueur = this.controleur.getJoueur();
        argent.setText(joueur.argentString());
        bouclier.setText(joueur.bouclierString());
        pv.setText(joueur.pointVictoireString());
        pdm.setText(joueur.pointDefaiteMilitaireString());
        pvm.setText(joueur.pointsVictoireMilitaireString());
        bouclier.setText(joueur.bouclierString());
        rouages.setText(joueur.rouagesString());
        compas.setText(joueur.compasString());
        tabelettes.setText(joueur.tablettesString());
        IDeck deck = this.controleur.getJoueur().getDeck();
        List<ImageView> im = new ArrayList<>();
        this.associationCarteImageview = new HashMap<>();
        for (ICarte carte: deck.getDeck())
        {
            ImageView imageView = carte.creationGraphique();

            im.add(imageView);
            associationCarteImageview.put(imageView,carte);
        }
        ObservableList<ImageView> observableList = FXCollections.observableList(im);
        lv.setItems(observableList);
        lv.setOrientation(Orientation.HORIZONTAL);
    }

    @Override
    public void chargerDonnees() throws RemoteException {
    }

    public void jouerCarte(ActionEvent actionEvent) throws Exception
    {
        this.boutonJouer.setOnAction(e->
        {
            try {
                this.controleur.jouerCarte();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        affichageInteractifDesVariables();

    }

    public void construireEtape(ActionEvent actionEvent) throws Exception
    {
        this.boutonEtape.setOnAction(e->
        {
            try {
                this.controleur.construireEtape();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void defausserCarte(ActionEvent actionEvent) throws Exception
    {
        this.boutonDefausser.setOnAction(e->
        {
            try {
                this.controleur.defausserCarte();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        System.out.println(this.controleur.getJoueur().getDeck().getDeck());
        affichageInteractifDesVariables();
        System.out.println(this.controleur.getJoueur().getDeck().getDeck());


    }

    public ICarte getCarte() {
        return carte;
    }

    public void voirInfoJDroite(ActionEvent actionEvent) {
        IJoueur j= this.controleur.getInfoJDroite();
        File file = new File("clientJfx/src/main/resources/images/");
        Image image = new Image(file.toURI().toString()+j.getMerveille().getImage());
        merveilleVoisinDroite.setImage(image);

    }

    public void voirInfoJGauche(ActionEvent actionEvent) {
        IJoueur j= this.controleur.getInfoJGauche();
        File file = new File("clientJfx/src/main/resources/images/");
        Image image = new Image(file.toURI().toString()+j.getMerveille().getImage());
        merveilleVoisinGauche.setImage(image);
    }

    public void voirInfoJFace(ActionEvent actionEvent) {
        IJoueur j= this.controleur.getInfoJFace();
        File file = new File("clientJfx/src/main/resources/images/");
        Image image = new Image(file.toURI().toString()+j.getMerveille().getImage());
        merveilleJoueurFace.setImage(image);
    }

    public void onClickAfficher(MouseEvent mouseEvent) throws RemoteException {
        if(mouseEvent.getClickCount() == 1)
        {
            ImageView imageView = (ImageView) lv.getSelectionModel().getSelectedItem();
            this.carte = associationCarteImageview.get(imageView);
        }
    }
}
