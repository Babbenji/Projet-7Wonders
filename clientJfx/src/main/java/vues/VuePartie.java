package vues;

import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Popup;
import joueur.Joueur;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @FXML
    private ImageView merveilleIM;

    @FXML
    ImageView merveilleJoueurFace;

    @FXML
    ImageView merveilleVoisinGauche;

    @FXML
    ImageView merveilleVoisinDroite;

    @FXML
    Button jDroite;




    private ImageView imageSelectionne;

    private IJoueur joueurGauche;
    private IJoueur joueurDroite;
    private IJoueur joueurEnFace;
    private Scene scene;
    private Controleur controleur;
    private Stage stage;
    private ICarte carte;


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
        affichageCarteInteractif();


    }
    public void affichageCarteInteractif() throws RemoteException {
        IDeck deck = this.controleur.getDeck();
        List<ImageView> im = new ArrayList<>();
        for (ICarte carte: deck.getDeck())
        {
            im.add(carte.creationGraphique());
        }
        ObservableList<ImageView> observableList = FXCollections.observableList(im);
        lv.setItems(observableList);
        lv.setOrientation(Orientation.HORIZONTAL);

    }

    @Override
    public void chargerDonnees() throws RemoteException {

        this.joueurGauche = this.controleur.getInfoJGauche();
        File file = new File("clientJfx/src/main/resources/images/");
        merveilleVoisinGauche.setImage(new Image(file.toURI().toString()+this.joueurGauche.getMerveille().getImage()));

        this.joueurDroite = this.controleur.getInfoJDroite();
        merveilleVoisinDroite.setImage(new Image(file.toURI().toString()+this.joueurDroite.getMerveille().getImage()));

        this.joueurEnFace = this.controleur.getInfoJFace();
        merveilleJoueurFace.setImage(new Image(file.toURI().toString()+this.joueurEnFace.getMerveille().getImage()));

    }

    public void jouerCarte(ActionEvent actionEvent) throws Exception
    {
        boutonJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lab.setText("Vous avez joué, en attente pour tour suivant");
            }
        });
        this.controleur.jouerCarte();
    }

    public void acheterEtape(ActionEvent actionEvent) throws Exception
    {
        this.controleur.construireEtape();

    }

    public void defausserCarte(ActionEvent actionEvent) throws Exception
    {

        this.controleur.defausserCarte();

    }
    public void choixCarteAJoue(MouseEvent mouseEvent) throws RemoteException {

        ImageView imageView = (ImageView) mouseEvent.getSource();
        if(mouseEvent.getClickCount()==2)
        {

        }
    }

    public ICarte getCarte() {
        return carte;
    }

    public void voirInfoJDroite(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Infos " + this.joueurDroite.getNom());
        DialogPane pane = new DialogPane();
        pane.setContentText(this.joueurDroite.getRessources().keySet().stream()
                .map(key -> key + "=" + this.joueurDroite.getRessources().get(key))
                .collect(Collectors.joining(", ")));
        alert.dialogPaneProperty().set(pane);

        alert.showAndWait();

    }

    public void voirInfoJGauche(ActionEvent actionEvent) {

    }

    public void voirInfoJFace(ActionEvent actionEvent) {

    }
}
