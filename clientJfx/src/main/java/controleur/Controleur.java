package controleur;

import javafx.stage.Stage;
import modele.ProxySevenWondersOnLine;
import modele.ProxySevenWondersOnLineImpl;
import vues.VueConnexion;
import vues.VueInscription;
import vues.VueMenuNonConnecte;

import java.io.IOException;

public class Controleur
{
    private ProxySevenWondersOnLine facade;
    private VueMenuNonConnecte vueMenuNonConnecte;
    private VueConnexion vueConnexion;
    private VueInscription vueInscription;



    public Controleur(Stage stage) throws IOException {
        facade = new ProxySevenWondersOnLineImpl();

        vueMenuNonConnecte = VueMenuNonConnecte.creerVue(stage);
        vueMenuNonConnecte.initialiserControleur(this);
        vueConnexion = VueConnexion.creerVue(stage);
        vueConnexion.initialiserControleur(this);
        vueInscription = VueInscription.creerVue(stage);
        vueInscription.initialiserControleur(this);

    }

    public void run()
    {
        vueMenuNonConnecte.show();
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

    public void exit() {
        System.exit(0);
    }
}



