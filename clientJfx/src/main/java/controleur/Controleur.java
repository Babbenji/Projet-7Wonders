package controleur;

import javafx.stage.Stage;
import modele.ProxySevenWondersOnLine;
import modele.ProxySevenWondersOnLineImpl;
import vues.VueMenuNonConnecte;

import java.io.IOException;

public class Controleur
{
    private ProxySevenWondersOnLine facade;
    private VueMenuNonConnecte vueMenuNonConnecte;


    public Controleur(Stage stage) throws IOException {
        facade = new ProxySevenWondersOnLineImpl();
        vueMenuNonConnecte = VueMenuNonConnecte.creerVue(stage);
    }

    public void run()
    {
        vueMenuNonConnecte.show();
    }
}
