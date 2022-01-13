package programmeTest;


import facade.FacadeSevenWondersOnlineImpl;
import joueur.Joueur;
import partie.Partie;
import type.ICarte;
import type.IJoueur;
import type.IMerveille;
import java.util.ArrayList;
import java.util.List;

public class TestFacadePartie
{
    public static void main(String[] args) throws Exception {
        IJoueur joueur1 = new Joueur("Aziz");
        IJoueur joueur2 = new Joueur("Juli");
        IJoueur joueur3 = new Joueur("Timo");
        IJoueur joueur4 = new Joueur("Math");
        ArrayList<IJoueur> lj = new ArrayList<>();
        lj.add(joueur1);
        lj.add(joueur2);
        lj.add(joueur3);
        lj.add(joueur4);

        FacadeSevenWondersOnlineImpl facade = new FacadeSevenWondersOnlineImpl();
        List<ICarte> lc = facade.getLesCartes();
        List<IMerveille> lm = facade.getLesMerveilles();

        System.out.println("--------- DEBUT D UNE PARTIE ---------");
        Partie partie = new Partie(lj,lc,lm);
        System.out.println("On mélanges les cartes pour que ca soit équitable");
        partie.miseEnPlacePartie();

        System.out.println("--------- LEURS MERVEILLES ------");

        System.out.println("merveille joueur1" + joueur1.getMerveille());
        System.out.println("m joueur2" + joueur2.getMerveille());
        System.out.println("m joueur3" + joueur3.getMerveille());
        System.out.println("m joueur4" + joueur4.getMerveille());

        System.out.println("-------- LEURS DECK --------");

        System.out.println("deck joueur1" + joueur1.getDeck());
        System.out.println("deck joueur2" + joueur2.getDeck());
        System.out.println("deck joueur3" + joueur3.getDeck());
        System.out.println("deck joueur4" + joueur4.getDeck());

        System.out.println("LES JOUEURS VONT A PRESENT JOUER UNE CARTE CHACUN");
        System.out.println("jouer1 joue");
        partie.jouerCarte(joueur1, joueur1.getDeck().getCarteDansDeck(0));
        System.out.println(joueur1.getDeck());

        System.out.println("jouer2 joue");
        partie.jouerCarte(joueur2, joueur2.getDeck().getCarteDansDeck(0));
        System.out.println(joueur2.getDeck());

        System.out.println("jouer3 joue");
        partie.jouerCarte(joueur3, joueur3.getDeck().getCarteDansDeck(0));
        System.out.println(joueur3.getDeck());

        System.out.println("jouer4 joue");
        partie.jouerCarte(joueur4, joueur4.getDeck().getCarteDansDeck(0));
        System.out.println(joueur4.getDeck());

        System.out.println("------On passe au tour suivant-------");
        partie.passerAuTourSuivant();
        System.out.println("deck joueur1" + joueur1.getDeck());
        System.out.println("deck joueur2" + joueur2.getDeck());
        System.out.println("deck joueur3" + joueur3.getDeck());
        System.out.println("deck joueur4" + joueur4.getDeck());

        System.out.println("------------TESTS PASSER AGE SUIVANTS ------------");
        partie.passerAgeSuivant();

        System.out.println("deck joueur1" + joueur1.getDeck());
        System.out.println("deck joueur2" + joueur2.getDeck());
        System.out.println("deck joueur3" + joueur3.getDeck());
        System.out.println("deck joueur4" + joueur4.getDeck());


        System.out.println("------------- TESTS DEFAUSSER CARTE ------------");
        partie.deffausserCarte(joueur1,joueur1.getDeck().getCarteDansDeck(0));
        System.out.println(partie.getCarteDefausse());
        System.out.println(partie.getCarteDefausse().size());


        System.out.println("-----------------TESTS ACHAT ETAPE CARTE-------------");







    }

}
