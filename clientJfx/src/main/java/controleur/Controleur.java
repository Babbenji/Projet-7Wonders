package controleur;

import interfaces.type.IDeck;
import interfaces.type.IJoueur;
import interfaces.type.IMerveille;
import javafx.stage.Stage;
import modele.ProxySevenWondersOnLine;
import modele.ProxySevenWondersOnLineImpl;
import vues.VueMenuNonConnecte;
import vues.VuePartie;

import java.io.IOException;
import java.rmi.RemoteException;

public class Controleur
{
    private ProxySevenWondersOnLine facade;
    private VueMenuNonConnecte vueMenuNonConnecte;
    private VuePartie vuePartie;
    private IJoueur joueur;
    private IDeck deck;
    private IMerveille merveille;


    public Controleur(Stage stage) throws IOException {
        facade = new ProxySevenWondersOnLineImpl();
        vueMenuNonConnecte = VueMenuNonConnecte.creerVue(stage);
        vuePartie = VuePartie.creerVue(stage);
    }

    public void run()
    {
        vuePartie.show();
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
}
