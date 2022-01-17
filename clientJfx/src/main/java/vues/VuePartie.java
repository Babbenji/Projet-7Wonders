package vues;

import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import merveilles.exceptions.MaximumEtapeAtteintException;
import partie.exceptions.ChoixDejaFaitException;
import partie.exceptions.PasAssezDeRessourcesException;
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
    @FXML
    public Label lab;
    @FXML
    public Button jGauche;
    @FXML
    public Button jFace;
    @FXML
    public Label argent;
    @FXML
    public Label pvm;
    @FXML
    public Label bouclier;
    @FXML
    public Label pdm;
    @FXML
    public Label pv;
    @FXML
    public Label rouages;
    @FXML
    public Label compas;
    @FXML
    public Label tabelettes;
    @FXML
    public Label bois;
    @FXML
    public Label pierres;
    @FXML
    public Label minerais;
    @FXML
    public Label briques;
    @FXML
    public Label tissus;
    @FXML
    public Label verres;
    @FXML
    public Label papiers;
    @FXML
    public Label pierresOuBriques;
    @FXML
    public Label mineraisOuBriques;
    @FXML
    public Label boisOuPierres;
    @FXML
    public Label boisOuBriquesOuPierresOuMinerais;
    @FXML
    public Label tissusOuVerresOuPapiers;
    @FXML
    private ImageView merveilleIM;
    @FXML
    ImageView merveilleJoueurFace;
    @FXML
    ImageView merveilleVoisinGauche;
    @FXML
    ImageView merveilleVoisinDroite;
    @FXML
    ImageView imageAge;
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
        IMerveille merveille = this.controleur.getJoueur().getMerveille();
        File file = new File("clientJfx/src/main/resources/images/");
        Image image = new Image(file.toURI().toString()+merveille.getImage());
        merveilleIM.setImage(image);
        affichageInteractifDesVariables();

    }
    public void affichageInteractifDesVariables() throws RemoteException {
        IJoueur joueur = this.controleur.getJoueur();
        int age= this.controleur.getPartie().getAgeEnCours();
        if(age == 1){
            Image imageAge = new Image("images/ph1.png");
            this.imageAge.setImage(imageAge);
        }else if(age== 2){
            Image imageAge = new Image("images/ph2.png");
            this.imageAge.setImage(imageAge);
        }else{
            Image imageAge = new Image("images/ph3.png");
            this.imageAge.setImage(imageAge);
        }
        argent.setText(joueur.argentString());
        bouclier.setText(joueur.bouclierString());
        pv.setText(joueur.pointVictoireString());
        pdm.setText(joueur.pointDefaiteMilitaireString());
        pvm.setText(joueur.pointsVictoireMilitaireString());
        bouclier.setText(joueur.bouclierString());
        rouages.setText(joueur.rouagesString());
        compas.setText(joueur.compasString());
        tabelettes.setText(joueur.tablettesString());
        bois.setText(joueur.getRessources().get("Bois").toString());
        pierres.setText(joueur.getRessources().get("Pierres").toString());
        briques.setText(joueur.getRessources().get("Briques").toString());
        minerais.setText(joueur.getRessources().get("Minerais").toString());
        tissus.setText(joueur.getRessources().get("Tissus").toString());
        verres.setText(joueur.getRessources().get("Verres").toString());
        papiers.setText(joueur.getRessources().get("Papiers").toString());
        pierresOuBriques.setText(joueur.getRessources().get("PierresOuBriques").toString());
        mineraisOuBriques.setText(joueur.getRessources().get("MineraisOuBriques").toString());
        boisOuPierres.setText(joueur.getRessources().get("BoisOuPierres").toString());
        boisOuBriquesOuPierresOuMinerais.setText(joueur.getRessources().get("BoisOuBriquesOuPierresOuMinerais").toString());
        tissusOuVerresOuPapiers.setText(joueur.getRessources().get("TissusOuVerresOuPapiers").toString());
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
    public void chargerDonnees() throws RemoteException
    {
        this.controleur.miseEnPlaceDuJeu();
    }
    public void debutpartie() throws RemoteException {
        IMerveille merveille = this.controleur.getJoueur().getMerveille();
        File file = new File("clientJfx/src/main/resources/images/");
        Image image = new Image(file.toURI().toString()+merveille.getImage());
        merveilleIM.setImage(image);
        affichageInteractifDesVariables();

        this.joueurGauche = this.controleur.getJoueurGauche();
        merveilleVoisinGauche.setImage(new Image(file.toURI().toString()+this.joueurGauche.getMerveille().getImage()));
        this.joueurDroite = this.controleur.getJoueurDroite();
        merveilleVoisinDroite.setImage(new Image(file.toURI().toString()+this.joueurDroite.getMerveille().getImage()));

        this.joueurEnFace = this.controleur.getJoueurFace();
        merveilleJoueurFace.setImage(new Image(file.toURI().toString()+this.joueurEnFace.getMerveille().getImage()));


    }

    public void jouerCarte(ActionEvent actionEvent) throws ChoixDejaFaitException, RemoteException, PasAssezDeRessourcesException {

        this.boutonJouer.setOnAction(e->
        {
            try {
                this.controleur.jouerCarte();
            }
            catch (RemoteException exception)
            {
                exception.printStackTrace();
            }
            catch (ChoixDejaFaitException exception ) {
                Alert alert =  new Alert(Alert.AlertType.ERROR,"VOUS AVEZ DEJA JOUEZ!! ATTENDEZ LE TOUR SUIVANT",ButtonType.OK);
                alert.showAndWait();
            }
            catch (PasAssezDeRessourcesException exception)
            {
                Alert alert =  new Alert(Alert.AlertType.ERROR,"Vous N avez pas les ressources ,necessaires",ButtonType.OK);
                alert.showAndWait();
            }
        });
        attendreChoixAdversaires(this.controleur.getJoueur().getAJoue());
        affichageInteractifDesVariables();
    }

    public void construireEtape(ActionEvent actionEvent) throws ChoixDejaFaitException,PasAssezDeRessourcesException,RemoteException
    {
        this.boutonEtape.setOnAction(e->
        {
            try {
                this.controleur.construireEtape();
            } catch ( RemoteException exception) {
                exception.printStackTrace();
            }
            catch (ChoixDejaFaitException exception ) {
                Alert alert =  new Alert(Alert.AlertType.ERROR,"VOUS AVEZ DEJA JOUEZ!! ATTENDEZ LE TOUR SUIVANT",ButtonType.OK);
                alert.showAndWait();
            }
            catch (PasAssezDeRessourcesException exception)
            {
                Alert alert =  new Alert(Alert.AlertType.ERROR,"Vous N avez pas les ressources ,necessaires",ButtonType.OK);
                alert.showAndWait();
            } catch (MaximumEtapeAtteintException maximumEtapeAtteintException) {
                Alert alert =  new Alert(Alert.AlertType.ERROR,"Vous avez deja construit toute vos etapes",ButtonType.OK);
                alert.showAndWait();
            }
        });
    }

    public void defausserCarte(ActionEvent actionEvent) throws ChoixDejaFaitException, RemoteException {
        this.boutonDefausser.setOnAction(e->
        {
            try {
                this.controleur.defausserCarte();
            } catch (RemoteException exception) {
                exception.printStackTrace();
            }
            catch (ChoixDejaFaitException exception)
            {
                Alert alert =  new Alert(Alert.AlertType.ERROR,"VOUS AVEZ DEJA JOUEZ!! ATTENDEZ LE TOUR SUIVANT",ButtonType.OK);
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        affichageInteractifDesVariables();
    }

    public void attendreChoixAdversaires(boolean ajoue)
    {
        IJoueur joueurD = this.controleur.getJoueurDroite();
        IJoueur joueurF = this.controleur.getJoueurGauche();
        IJoueur joueurG = this.controleur.getJoueurFace();
        Task<Boolean> attenteCoup = new Task<Boolean>()
        {
            @Override
            protected Boolean call() throws Exception
            {
                Boolean AjoueD = null;
                Boolean AjoueG = null;
                Boolean AjoueF = null;
                do
                {
                    AjoueD = joueurD.getAJoue();
                    AjoueF = joueurF.getAJoue();
                    AjoueG = joueurG.getAJoue();
                }
                    while (!AjoueD || !AjoueF || !AjoueG || !ajoue);
                    return true;
                }
            };
        attenteCoup.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> controleur.passerALaSuite());
        Thread thread = new Thread(attenteCoup);
        thread.start();
    }


    public ICarte getCarte() {
        return carte;
    }

    public void voirInfoJDroite(ActionEvent actionEvent) {
        Label argentD = new Label();
        Label pvmD = new Label();
        Label bouclierD = new Label();
        Label pdmD = new Label();
        Label pvD = new Label();
        Label rouagesD = new Label();
        Label compasD = new Label();
        Label tabelettesD = new Label();

        final Stage dialog = new Stage();
        dialog.setTitle("Informations de " +this.joueurDroite.getNom());

        dialog.initModality(Modality.NONE);
        dialog.initOwner(this.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text(this.joueurDroite.getRessources().keySet().stream()
                .map(key -> key + "=" + this.joueurDroite.getRessources().get(key))
                .collect(Collectors.joining(System.getProperty("line.separator")))));
        argentD.setText(this.joueurDroite.argentString());
        bouclierD.setText(joueurDroite.bouclierString());
        pvD.setText(joueurDroite.pointVictoireString());
        pdmD.setText(joueurDroite.pointDefaiteMilitaireString());
        pvmD.setText(joueurDroite.pointsVictoireMilitaireString());
        rouagesD.setText(joueurDroite.rouagesString());
        compasD.setText(joueurDroite.compasString());
        tabelettesD.setText(joueurDroite.tablettesString());

        dialogVbox.getChildren().add(argentD);
        dialogVbox.getChildren().add(bouclierD);
        dialogVbox.getChildren().add(pvD);
        dialogVbox.getChildren().add(pdmD);
        dialogVbox.getChildren().add(pvmD);
        dialogVbox.getChildren().add(rouagesD);
        dialogVbox.getChildren().add(compasD);
        dialogVbox.getChildren().add(tabelettesD);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();

    }

    public void voirInfoJGauche(ActionEvent actionEvent) {

        Label argentG = new Label();
        Label pvmG = new Label();
        Label bouclierG = new Label();
        Label pdmG = new Label();
        Label pvG = new Label();
        Label rouagesG = new Label();
        Label compasG = new Label();
        Label tabelettesG = new Label();

        final Stage dialog = new Stage();
        dialog.setTitle("Informations de " +this.joueurGauche.getNom());
        dialog.initModality(Modality.NONE);
        dialog.initOwner(this.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text(this.joueurGauche.getRessources().keySet().stream()
                .map(key -> key + "=" + this.joueurGauche.getRessources().get(key))
                .collect(Collectors.joining(System.getProperty("line.separator")))));
        argentG.setText(this.joueurGauche.argentString());
        bouclierG.setText(joueurGauche.bouclierString());
        pvG.setText(joueurGauche.pointVictoireString());
        pdmG.setText(joueurGauche.pointDefaiteMilitaireString());
        pvmG.setText(joueurGauche.pointsVictoireMilitaireString());
        rouagesG.setText(joueurGauche.rouagesString());
        compasG.setText(joueurGauche.compasString());
        tabelettesG.setText(joueurGauche.tablettesString());

        dialogVbox.getChildren().add(argentG);
        dialogVbox.getChildren().add(bouclierG);
        dialogVbox.getChildren().add(pvG);
        dialogVbox.getChildren().add(pdmG);
        dialogVbox.getChildren().add(pvmG);
        dialogVbox.getChildren().add(rouagesG);
        dialogVbox.getChildren().add(compasG);
        dialogVbox.getChildren().add(tabelettesG);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();

    }

    public void voirInfoJFace(ActionEvent actionEvent) {
        Label argentF = new Label();
        Label pvmF = new Label();
        Label bouclierF = new Label();
        Label pdmF = new Label();
        Label pvF = new Label();
        Label rouagesF = new Label();
        Label compasF = new Label();
        Label tabelettesF = new Label();

        final Stage dialog = new Stage();
        dialog.setTitle("Informations de " +this.joueurEnFace.getNom());

        dialog.initModality(Modality.NONE);
        dialog.initOwner(this.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text(this.joueurEnFace.getRessources().keySet().stream()
                .map(key -> key + "=" + this.joueurEnFace.getRessources().get(key))
                .collect(Collectors.joining(System.getProperty("line.separator")))));

        argentF.setText(this.joueurEnFace.argentString());
        bouclierF.setText(joueurEnFace.bouclierString());
        pvF.setText(joueurEnFace.pointVictoireString());
        pdmF.setText(joueurEnFace.pointDefaiteMilitaireString());
        pvmF.setText(joueurEnFace.pointsVictoireMilitaireString());
        rouagesF.setText(joueurEnFace.rouagesString());
        compasF.setText(joueurEnFace.compasString());
        tabelettesF.setText(joueurEnFace.tablettesString());

        dialogVbox.getChildren().add(argentF);
        dialogVbox.getChildren().add(bouclierF);
        dialogVbox.getChildren().add(pvF);
        dialogVbox.getChildren().add(pdmF);
        dialogVbox.getChildren().add(pvmF);
        dialogVbox.getChildren().add(rouagesF);
        dialogVbox.getChildren().add(compasF);
        dialogVbox.getChildren().add(tabelettesF);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void onClickAfficher(MouseEvent mouseEvent) throws RemoteException {
        if(mouseEvent.getClickCount() == 1)
        {
            ImageView imageView = (ImageView) lv.getSelectionModel().getSelectedItem();
            this.carte = associationCarteImageview.get(imageView);
        }

    }
}
