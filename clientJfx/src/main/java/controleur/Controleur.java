package controleur;

import facade.FacadeSevenWondersOnlineImpl;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import type.*;
import services.exceptions.PseudoDejaPrisException;
import type.IDeck;
import type.IJoueur;
import type.IMerveille;
import javafx.stage.Stage;
import modele.ProxySevenWondersOnLine;
import modele.ProxySevenWondersOnLineImpl;
import user.User;
import vues.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class Controleur
{
    private ProxySevenWondersOnLine facade;

    private VueMenuNonConnecte vueMenuNonConnecte;
    private VueConnexion vueConnexion;
    private VueInscription vueInscription;
    private VueMenuConnecte vueMenuConnecte;
    private VuePartie vuePartie;

    private IJoueur joueur;
    private IDeck deck;
    private IMerveille merveille;
    private String nom;
    private User user;
    FacadeSevenWondersOnlineImpl facadeta = new FacadeSevenWondersOnlineImpl();
    IPartie partie = facadeta.creePartie();;

    public Controleur(Stage stage) throws IOException, NotBoundException {
        facade = new ProxySevenWondersOnLineImpl();

        vueMenuNonConnecte = VueMenuNonConnecte.creerVue(stage);
        vueMenuNonConnecte.initialiserControleur(this);
        vueConnexion = VueConnexion.creerVue(stage);
        vueConnexion.initialiserControleur(this);
        vueInscription = VueInscription.creerVue(stage);
        vueInscription.initialiserControleur(this);
        vueMenuConnecte = VueMenuConnecte.creer(stage);
        vueMenuConnecte.initialiserControleur(this);
        vuePartie = VuePartie.creerVue(stage,this);


    }

    public void run() throws RemoteException {

        vuePartie.show();
    }

    public void miseEnPlaceDuJeu() throws RemoteException {


//        IJoueur joueur1 = new Joueur("Aziz");
//        ArrayList<IJoueur> lj = new ArrayList<>();
//        lj.add(joueur1);
//        List<ICarte> lc = facade.getLesCartes();
//        List<IMerveille> lm = facade.getLesMerveilles();

        partie.miseEnPlacePartie();
        joueur = partie.getListeDesJoueurs().get(0);
        this.merveille = partie.getListeDesJoueurs().get(0).getMerveille();
        this.deck = partie.getListeDesJoueurs().get(0).getDeck();
    }

    public ProxySevenWondersOnLine getFacade() {
        return facade;
    }

    public IJoueur getJoueur() {
        return joueur;
    }

    public IDeck getDeck() throws RemoteException
    {
        return deck ;
    }

    public IMerveille getMerveille() throws RemoteException {
        return this.merveille;
    }


    public void goToMenu() {
        this.vueMenuNonConnecte.show();
    }

    public void goToMenuConnecte()
    {
        vueMenuConnecte.chargerDonnees();
        this.vueMenuConnecte.show();
    }

    public void goToConnexion()  {
        this.vueConnexion.show();
    }

    public void goToInscription() {
        this.vueInscription.show();
    }

    public String getNom() {
        return this.nom;
    }

    public Boolean connexion(String pseudo,String mdp) throws PseudoOuMotDePasseIncorrectException, RemoteException, NotBoundException, MalformedURLException {
        boolean connexionReussie = false;
        this.user = this.facade.connexionUser(pseudo,mdp);
        if(!Objects.isNull(this.user))
        {
            connexionReussie = true;
            this.nom = pseudo;
            this.goToMenuConnecte();
        }
        return connexionReussie;
    }

    public Boolean inscription(String pseudo,String mdp) throws PseudoDejaPrisException, RemoteException, NotBoundException, MalformedURLException {
        boolean inscriptionReussie = false;
        this.user = this.facade.inscriptionUser(pseudo,mdp);
        if(!Objects.isNull(this.user)){
            inscriptionReussie = true;
        }
        return inscriptionReussie;
    }

    public List<User> getAmis() {
        return this.user.getAmis();
    }
    public void exit() {
        System.exit(0);
    }


    public void construireEtape() throws Exception {
        this.facade.construireEtape(joueur);
    }

    public void defausserCarte() throws Exception {
        MouseEvent mouseEvent ;
        vuePartie.choixCarteAJoue(mouseEvent);
        partie.deffausserCarte(joueur, vuePartie.getCarte());
    }

    public void jouerCarte() throws Exception {
        ICarte carte = vuePartie.getCarte();
        partie.jouerCarte(joueur, carte);
        this.deck = partie.getListeDesJoueurs().get(0).getDeck();

    }
}
