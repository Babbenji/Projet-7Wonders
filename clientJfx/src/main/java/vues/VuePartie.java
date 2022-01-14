package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import type.ICarte;
import type.IDeck;
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
import java.util.Iterator;

public class VuePartie implements Vue
{
    @FXML
    public HBox boxcarte;

    @FXML
    private ImageView merveilleIM;

    public ImageView carte_2;

    @FXML
    public ImageView carte_1;

    @FXML
    public ImageView carte_3;

    @FXML
    public ImageView carte_4;

    @FXML
    public ImageView carte_5;

    @FXML
    public ImageView carte_6;

    @FXML
    public ImageView carte_7;

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
        this.controleur.miseEnPlace();
        IMerveille merveille = this.controleur.getMerveille();
        IDeck deck = this.controleur.getDeck();
        File file = new File("clientJfx/src/main/resources/images/");
        Image image = new Image(file.toURI().toString()+merveille.getImage());merveilleIM.setImage(image);

        Iterator<Node> iterator = boxcarte.getChildren().iterator();
//        if (iterator.hasNext()){
//            Image image1 = new Image(file.toURI().toString()+)
//        }
        //carte_1.setImage();

    }

    @Override
    public void chargerDonnees() throws RemoteException {
    }

    public void jouerCarte(ActionEvent actionEvent) throws Exception {

        this.controleur.jouerCarte(this.carte);
    }

    public void acheterEtape(ActionEvent actionEvent) throws Exception
    {
        this.controleur.construireEtape();
    }

    public void defausserCarte(ActionEvent actionEvent) throws Exception
    {
        this.controleur.defausserCarte();
    }
    public void choixCarteAJoue(MouseEvent mouseEvent)
    {
        Image image = (Image) mouseEvent.getSource();

    }
}
