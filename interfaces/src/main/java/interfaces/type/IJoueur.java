package interfaces.type;

import cartes.Carte;
import cartes.Deck;
import joueur.Joueur;
import merveilles.Merveille;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IJoueur {
    //retourne le nom du joueur
    String getNom();

    //définit le nom du joueur
    void setNom(String nouveauNom);

    //retourne toutes les cartes jouées par le joueur
    ArrayList<Carte> getCartesJouees();

    // Ici on calcule le nombre de carte par type pour pouvoir appliquer les effets des Batiments de commerces et les Guildes
    int getNbreCarteMatierePremiere();

    int getNbreCarteProduitManufacture();

    int getNbreCarteBatimentCommercial();

    int getNbreCarteBatimentMilitaire();

    int getNbreCarteBatimentCivil();

    int getNbreCarteGuilde();

    int getNbreCarteBatimentScientifique();

    //ajoute une carte à la liste des cartes jouées par le joueur
    void addCartesJouees(ICarte laCarte);

    //Vérifie si le joueur a posé une carte en particulier et fournie en paramètre
    boolean aCarte(Carte carte);

    //retourne une table de hachage listant toutes les ressources du joueur sous forme <nom, quantité>
    HashMap<String, Integer> getRessources();

    //définit les ressources du joueur. Généré par Generate mais semble inutile à priori
    void setRessources(HashMap<String, Integer> lesRessources);

    //Donne au joueur n ressources dont le nom est fourni
    void augmenterRessource(String nom, int n);

    //permet d'augmenter les ressources du joueur selon une table de hachage de plusieurs augmentations <nom de la ressource, quantité>
    void augmenterPlusieursRessources(HashMap<String, Integer> listeAugmentations);

    void augmenterPlusieursRess(int n, String... r);

    //retourne si oui ou non le joueur a "qte" fois ou plus la ressource "nom"
    boolean aAssezRessource(String nom, int qte);

    //retourne la quantité que le joueur possède de telle ressource dont le nom est fourni en paramètre
    int getNbRessources(String nom);

    //retourne la Merveille du Joueur
    Merveille getMerveille();

    //Assigne une merveille à un joueur et lui confère les éventuels bonus initiaux associés à celle-ci
    void setMerveille(Merveille laMerveille);

    //retourne la fortune du joueur
    int getPieces();

    //ajoute n pieces au joueur
    void addPieces(int n);

    //enleve n pièces au joueur
    void enleverPieces(int n);

    //verifie si le joueur est prêt pour la suite
    boolean isEtatJeu();

    //definit si le joueur est prêt pour la suite
    void setEtatJeu(boolean nouvelEtat);

    //definit l'état du joueur quant au commerce de matières premières avec le voisin de Droite
    void setCommerceMatieresPremieresDroite(boolean nouvelEtat);

    //definit l'état du joueur quant au commerce de matières premières avec le voisin de Gauche
    void setCommerceMatieresPremieresGauche(boolean nouvelEtat);

    //retourne l'état du joueur quant au commerce de de produits manufactures
    boolean isCommerceProduitsManufactures();

    //definit l'état du joueur quant au commerce de Produits Manufactures
    void setCommerceProduitsManufactures(boolean nouvelEtat);

    //retourne le total de pts de victoire du joueur
    int getPtsVictoire();

    //définit un nv nb de points de victoire. généré par le Generate mais est-ce utile ? (sinon, à suppr de préférence et à la fin du projet)
    void setPtsVictoire(int qte);

    //Ajoute n pts de victoire au joueur
    void addPtsVictoire(int n);

    //retourne le nb de Tablettes Scientifiques du joueur
    int getNbTablettes();

    //retourne le nb de Rouages Scientifiques du joueur
    int getNbRouages();

    //retourne le nb de Compas Scientifiques du joueur
    int getNbCompas();

    //Ajoute n tablettes scientifiques au joueur
    void addTablettes(int n);

    //Ajoute n rouages scientifiques au joueur
    void addRouages(int n);

    //Ajoute n compas scientifiques au joueur
    void addCompas(int n);

    //Ajoute n compas scientifiques au joueur
    void addSymboleUniversel(int n);

    //retourne le nb de symboles rouges (puissance militaire) du joueur
    int getPuissanceMilitaire();

    //ajoute n symboles rouges (puissance Militaire) au joueur
    void addPuissanceMilitaire(int n);

    //retourne le total de pts de victoire militaire
    int getPtsVictoireMilitaire();

    //definit le nb de points de victoire militaire. Généré par Generate mais est-ce vraiment utile ?
    void setPtsVictoireMilitaire(int nouveauxPtsVictoireMilitaire);

    //retourne le nb de jetons de défaite militaire du joueur
    int getNbJetonsDefaite();

    //définit le nb de jetons de défaite militaire du joueur. généré par generate mais est-ce vraiment utile ?
    void setNbJetonsDefaite(int nouveauNbJetonsDefaite);

    //ajoute n points de victoire militaire au joueur
    void addPointsVictoireMilitaire(int n);

    //ajoute n jetons de défaite militaire au joueur
    void addJetonsDefaiteMilitaire(int n);

    //retire 1 point de victoire militaire au joueur, ce qui correspond à une défaite (dans l,optique, cette fonction sera privilégiée pour le calcul final)
    void retirerPointsVictoireMilitaire();

    //retire n jetons de défaite militaire au joueur. Fonction prévue mais potentiellement inutile
    void retirerJetonsDefaiteMilitaire(int n);

    void setPuissanceMilitaire(int puissanceMilitaire);

    void setPieces(int pieces);

    void setCartesJouees(ArrayList<Carte> cartesJouees);

    void setNbTablettes(int nbTablettes);

    void setNbRouages(int nbRouages);

    void setNbCompas(int nbCompas);

    Deck getDeck();

    void setDeck(Deck deck);

    List<Joueur> getAmis();

    void setAmis(List<Joueur> amis);

    Boolean getAJoue();

    void setAJoue(Boolean AJoue);

    boolean isCommerceMatieresPremieresDroite();

    boolean isCommerceMatieresPremieresGauche();
}
