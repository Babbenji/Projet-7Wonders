package controleur;

import interfaces.type.IDeck;
import interfaces.type.IJoueur;
import interfaces.type.IMerveille;
import javafx.stage.Stage;
import modele.ProxySevenWondersOnLine;
import modele.ProxySevenWondersOnLineImpl;
import user.User;
import vues.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

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


    public Controleur(Stage stage) throws IOException {
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

    public void goToMenu() {
        this.facade = new ProxySevenWondersOnLineImpl();
        this.vueMenuNonConnecte.show();
    }

    public void goToConnexion() {
        this.facade = new ProxySevenWondersOnLineImpl();
        this.vueConnexion.show();
    }

    public void goToInscription() {
        this.facade = new ProxySevenWondersOnLineImpl();
        this.vueInscription.show();
    }
    public String getNom() {
        return this.joueur.getNom();
    }

    public List<User> getAmis() {
        return null;
    }

    public void exit() {
        System.exit(0);
    }
}
