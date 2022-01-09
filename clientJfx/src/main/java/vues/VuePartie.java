package vues;

import controleur.Controleur;
import type.IDeck;
import type.IJoueur;
import type.IMerveille;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class VuePartie implements Vue
{

    private Scene scene;
    private Controleur controleur;
    private Stage stage;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private AnchorPane anchorPartie;
    @FXML
    private ImageView merveille_1;
    @FXML
    private ImageView merveille_2;
    @FXML
    private ImageView merveille_3;
    @FXML
    private ImageView merveille_4;
    @FXML
    private ImageView carte_1;
    @FXML
    private ImageView carte_2;
    @FXML
    private ImageView carte_3;
    @FXML
    private ImageView carte_4;
    @FXML
    private ImageView carte_5;
    @FXML
    private ImageView carte_6;
    @FXML
    private ImageView carte_7;


    public static VuePartie creerVue(Stage stage)
    {
        URL location = VuePartie.class.getResource("partie.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VuePartie vue = fxmlLoader.getController();
        vue.setStage(stage);
        Scene scene = new Scene(root);
        vue.setScene(scene);
        return vue;
    }

    @Override
    public void show()
    {
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    @Override
    public void chargerDonnees()
    {

    }

    @Override
    public void initialiserControleur(Controleur controleur)
    {
        this.controleur = controleur;
    }
    public void initialiserPartie(IJoueur joueur)
    {
        IJoueur joueur1 = this.controleur.getJoueur();
        IDeck deck = this.controleur.getDeck();
        IMerveille merveille = this.controleur.getMerveille();

    }

}
