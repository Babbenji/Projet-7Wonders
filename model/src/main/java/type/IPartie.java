package type;

import merveilles.exceptions.MaximumEtapeAtteintException;
import partie.exceptions.ChoixDejaFaitException;
import partie.exceptions.PasAssezDeRessourcesException;

import java.util.ArrayList;
import java.util.List;

public interface IPartie {
    void constructionDesListes();

    void miseEnPlacePartie();

    void jouerCarte(IJoueur joueur, ICarte carte) throws ChoixDejaFaitException, PasAssezDeRessourcesException;

    void deffausserCarteFinAge() throws ChoixDejaFaitException;

    void deffausserCarte(IJoueur joueur, ICarte carte) throws ChoixDejaFaitException;


    void construireEtape(IJoueur p, ICarte carte) throws ChoixDejaFaitException, PasAssezDeRessourcesException, MaximumEtapeAtteintException;

    void passerAuTourSuivant();

    void passerAgeSuivant();

    int voisinDeDroite(int indice);

    int voisinDeGauche(int indice);

    void conflitsMilitaire();

    boolean finAge();

    boolean toutLeMondeAJoue();

    boolean finDernierTourDernierAge();

    void partieTerminee() throws Exception;

    //void suitePartie() throws Exception;

    void comptagePointVictoirePourBatimentScientifique(IJoueur joueur);

    void ajoutPointVictoireEnFinPartie() throws Exception;

    String affichageDesScores();

    int getIdPartie();

    void ajoutPointVictoireDuTresor(IJoueur joueur);

    void ajoutPointVictoireConflitsMilitaire(IJoueur joueur);

    ArrayList<IJoueur> getListeDesJoueurs();

    void setListeDesJoueurs(ArrayList<IJoueur> listeDesJoueurs);

    void addJoueur(IJoueur joueur);

    int getNB_JOUEURS();

    int getNB_CARTES();

    int getNB_MERVEILLES();

    ArrayList<ICarte> getCarteDefausse();

    void setCarteDefausse(ArrayList<ICarte> carteDefausse);

    ArrayList<ICarte> getCartesAgeI();

    void setCartesAgeI(ArrayList<ICarte> cartesAgeI);

    ArrayList<ICarte> getCartesAgeII();

    void setCartesAgeII(ArrayList<ICarte> cartesAgeII);

    ArrayList<ICarte> getCartesAgeIII();

    void setCartesAgeIII(ArrayList<ICarte> cartesAgeIII);

    List<ICarte> getCartes();

    void setCartes(ArrayList<ICarte> cartes);

    List<IMerveille> getMerveilles();
    void suitePartie();
}
