package controleur;

import services.exceptions.PseudoOuMotDePasseIncorrectException;
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
import java.util.List;
import java.util.Objects;

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
        /*
        vuePartie = VuePartie.creerVue(stage);
        vuePartie.initialiserControleur(this);
        */
    }

    public void run()
    {
        vueMenuNonConnecte.show();
    }

    public void miseEnPlaceDuJeu(IJoueur joueur) throws RemoteException {
        this.facade.miseEnPlaceDuJeu(joueur);
    }

    public ProxySevenWondersOnLine getFacade() {
        return facade;
    }

    public IJoueur getJoueur() {
        return joueur;
    }

    public IDeck getDeck() {
        return deck;
    }

    public IMerveille getMerveille() {
        return merveille;
    }

    public void goToMenu() throws RemoteException, NotBoundException, MalformedURLException {
        this.facade = new ProxySevenWondersOnLineImpl();
        this.vueMenuNonConnecte.show();
    }

    public void goToMenuConnecte() throws RemoteException, NotBoundException, MalformedURLException {
        this.facade = new ProxySevenWondersOnLineImpl();
        vueMenuConnecte.chargerDonnees();
        this.vueMenuConnecte.show();
    }

    public void goToConnexion() throws RemoteException, NotBoundException, MalformedURLException {
        this.facade = new ProxySevenWondersOnLineImpl();
        this.vueConnexion.show();
    }

    public void goToInscription() throws RemoteException, NotBoundException, MalformedURLException {
        this.facade = new ProxySevenWondersOnLineImpl();
        this.vueInscription.show();
    }

    public String getNom() {
        return this.nom;
    }

    public Boolean connexion(String pseudo,String mdp) throws PseudoOuMotDePasseIncorrectException, RemoteException, NotBoundException, MalformedURLException {
        boolean connexionReussie = false;
        this.user = this.facade.connexionUser(pseudo,mdp);
        if(!Objects.isNull(this.user)){
            connexionReussie = true;
            this.nom = pseudo;
            this.goToMenuConnecte();

        }
        return connexionReussie;
    }

    public List<User> getAmis() {
        return this.user.getAmis();
    }

    public void exit() {
        System.exit(0);
    }

}
