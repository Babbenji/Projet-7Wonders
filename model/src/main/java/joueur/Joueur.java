package joueur;

import type.ICarte;
import type.IDeck;
import type.IJoueur;
import type.IMerveille;
import merveilles.*;
import cartes.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Joueur implements IJoueur, Serializable {

    private String nom;
    private ArrayList<ICarte> cartesJouees; //les cartes jouées par le joueur
    private HashMap<String,Integer> ressources; //ressources du joueur, ce qui comprend les matieres premieres et les produits manufactures
    private Merveille merveille; //merveille du joueur
    private Deck deck;
    private List<IJoueur> amis;
    private int pieces;   //argent du joueur
    private boolean AJoue;

    //booleens d'état
    private boolean etatJeu; //définit si le joueur est prêt ou non
    private boolean commerceMatieresPremieresDroite,commerceMatieresPremieresGauche, commerceProduitsManufactures; //definit si le joueur est en état de commercer et ce pour les deux types principaux de ressources

    private int ptsVictoire; //nb de points de Victoire simples (symboles aux lauriers)

    //Nombres de symboles scientifiques
    private int nbTablettes;
    private int nbRouages;
    private int nbCompas;
    private int nbSymboleUniversel;

    //Attributs liés à la guerre
    private int puissanceMilitaire; //Nombre de symboles rouges
    private int ptsVictoireMilitaire; //pts de victoire dus à la guerre (total mis à jour avec des valeurs dépendantes de l'âge de chaque guerre)
    private int nbJetonsDefaite; //nb de points de défaite (une défaite vaut -1 quelque soit l'âge)


    //constructeur
    public Joueur(String nom){  //Constructeur, au début, seul le nom différencie les joueurs
        this.nom = nom;
        pieces = 3; //On débute avec 3 pièces
        cartesJouees = new ArrayList<ICarte>();
        etatJeu = false;
        commerceMatieresPremieresDroite = false;
        commerceMatieresPremieresGauche = false;
        commerceProduitsManufactures = false;
        ressources = new HashMap<String, Integer>(); // on initialise les ressources du joueur, à 0 de toute façon comme le choix des merveilles n'a pas été fait
        ressources.put("Pierres",0);
        ressources.put("Verres",0); //Le verre est un produit manufacture
        ressources.put("Papiers",0); //Le papier est un produit manufacture
        ressources.put("Bois",0);
        ressources.put("Tissus",0); //le tissu est un produit manufacture
        ressources.put("Briques",0);
        ressources.put("Minerais",0);
        ressources.put("PierresOuBriques",0); //Pour la carte "Excavation"                        \
        ressources.put("MineraisOuBriques",0); //Pour la carte "Fosse argileuse"                   \ Ces cartes offrent une ressource parmi une liste, on décide de créer des ressources spécifiques à ces cartes pour gérer le choix autrement
        ressources.put("BoisOuPierres",0); //Pour la carte "Exploitation Forestiere"               /
        ressources.put("BoisOuBriquesOuPierresOuMinerais",0); //Pour la carte "Caravanserail"     /
        ressources.put("TissusOuVerresOuPapiers",0); //Pour la carte "Forum"                     /
        ptsVictoire = 0;
        ptsVictoireMilitaire = 0;
        puissanceMilitaire = 0;
        nbJetonsDefaite = 0;
        nbCompas = 0;
        nbRouages = 0;
        nbTablettes = 0;
        nbSymboleUniversel = 0; // sert pour l'étape 2 d'Alexandria et pour la Guilde des scientifiques (gain de symbole scientifique selon ce qui est le plus rentable)
        merveille = null;  // le joueur n'aura pas encore choisi de merveille à sa création
        this.deck = null;
        this.amis = null;
        this.AJoue = false;
    }

    //Fonctions triées par attribu ciblé

    //nom

    //retourne le nom du joueur
    @Override
    public String getNom() {
        return nom;
    }

    //définit le nom du joueur
    @Override
    public void setNom(String nouveauNom) {
        nom = nouveauNom;
    }


    //Cartes jouees

    //retourne toutes les cartes jouées par le joueur
    @Override
    public ArrayList<ICarte> getCartesJouees() {
        return cartesJouees;
    }

    // Ici on calcule le nombre de carte par type pour pouvoir appliquer les effets des Batiments de commerces et les Guildes
    @Override
    public  int getNbreCarteMatierePremiere() {
        int nbreCarteMatierePremiere = 0;
        for (ICarte carte : cartesJouees) {
            if (carte.getType().equals("Matieres Premieres")) {
                nbreCarteMatierePremiere += 1;
            }
        }
        return nbreCarteMatierePremiere;
    }

    @Override
    public  int getNbreCarteProduitManufacture() {
        int nbreCarteProduitManufacture = 0;;
        for (ICarte carte : cartesJouees) {

            if (carte.getType().equals("Produit Manufacture"))
            {
                nbreCarteProduitManufacture += 1 ;
            }
        }
        return nbreCarteProduitManufacture;
    }
    @Override
    public  int getNbreCarteBatimentCommercial() {
        int nbreCarteBatimentCommercial = 0;;
        for (ICarte carte : cartesJouees) {

            if (carte.getType().equals("Batiment Commercial"))
            {
                nbreCarteBatimentCommercial += 1 ;
            }
        }
        return nbreCarteBatimentCommercial;
    }
    @Override
    public  int getNbreCarteBatimentMilitaire() {
        int nbreCarteBatimentMilitaire = 0;;
        for (ICarte carte : cartesJouees) {
            if (carte.getType().equals("Batiment Commercial"))
            {
                nbreCarteBatimentMilitaire += 1 ;
            }
        }

        return nbreCarteBatimentMilitaire;
    }

    @Override
    public  int getNbreCarteBatimentCivil() {
        int nbreCarteBatimentCivil = 0;;
        for (ICarte carte : cartesJouees) {
            if (carte.getType().equals("Batiment Commercial"))
            {
                nbreCarteBatimentCivil += 1 ;
            }
        }

        return nbreCarteBatimentCivil;
    }

    @Override
    public  int getNbreCarteGuilde() {
        int nbreCarteGuilde = 0;;
        for (ICarte carte : cartesJouees) {
            if (carte.getType().equals("Guilde"))
            {
                nbreCarteGuilde += 1 ;
            }
        }

        return nbreCarteGuilde;
    }
    @Override
    public  int getNbreCarteBatimentScientifique()
    {
        int nbreCarteBatimentScientifique = 0;
        for (ICarte carte : cartesJouees) {
            if (carte.getType().equals("Guilde"))
            {
                nbreCarteBatimentScientifique += 1 ;
            }
        }
        return nbreCarteBatimentScientifique;
    }

    //ajoute une carte à la liste des cartes jouées par le joueur
    @Override
    public void addCartesJouees(ICarte laCarte) {
        cartesJouees.add(laCarte);
    }


    //Ressources

    //retourne une table de hachage listant toutes les ressources du joueur sous forme <nom, quantité>
    @Override
    public HashMap<String, Integer> getRessources() {
        return ressources;
    }

    //définit les ressources du joueur. Généré par Generate mais semble inutile à priori
    @Override
    public void setRessources(HashMap<String, Integer> lesRessources) {
        ressources = lesRessources;
    }

    //Donne au joueur n ressources dont le nom est fourni
    @Override
    public void augmenterRessource(String nom, int n) {
        ressources.put(nom, ressources.get(nom) + n);
    }

    //permet d'augmenter les ressources du joueur selon une table de hachage de plusieurs augmentations <nom de la ressource, quantité>
    @Override
    public void augmenterPlusieursRessources(HashMap<String, Integer> listeAugmentations) {
        for (HashMap.Entry<String,Integer> e : listeAugmentations.entrySet()) {
            ressources.put(e.getKey(), ressources.get(e.getKey()) + e.getValue());
            // IMPORTANT : à tester car exception relevée de mon côté hors de ce git : la retrouver avec sa cause puis gérer avec un try/catch
            //Sinon : trouver une autre façon de procéder à l'itération de chaque valeur
        }
    }
    @Override
    public void augmenterPlusieursRess(int n, String... r){
        for (String ressource: r) {
            ressources.put(ressource,1);
        }
    }

    //retourne si oui ou non le joueur a "qte" fois ou plus la ressource "nom"
    @Override
    public boolean aAssezRessource(String nom, int qte){
        return ressources.get(nom) >= qte;

    }

    //retourne la quantité que le joueur possède de telle ressource dont le nom est fourni en paramètre
    @Override
    public int getNbRessources(String nom) {
        return ressources.get(nom);
    }


    //Merveille

    //retourne la Merveille du Joueur
    @Override
    public Merveille getMerveille() {
        return merveille;
    }

    //Assigne une merveille à un joueur et lui confère les éventuels bonus initiaux associés à celle-ci
    @Override
    public void setMerveille(IMerveille laMerveille) {
        merveille = (Merveille) laMerveille;
        // TO DO : rajouter au passage les bénéfices initiaux de la merveille au joueur ! (ressources, pièces... ?)
    }


    //Pièces

    //retourne la fortune du joueur
    @Override
    public int getPieces() {
        return pieces;
    }

    //ajoute n pieces au joueur
    @Override
    public void addPieces(int n) {
        pieces += n;
    }

    //enleve n pièces au joueur
    @Override
    public void enleverPieces(int n){
        pieces -= n;
        if(pieces<0)
            pieces=0;  //Au cas où, bien que ça ne paraisse pas possible, le retrait dépasse la barre du positif ou nul du solde
    }


    //Les états

    //verifie si le joueur est prêt pour la suite
    @Override
    public boolean isEtatJeu() {
        return etatJeu;
    }

    //definit si le joueur est prêt pour la suite
    @Override
    public void setEtatJeu(boolean nouvelEtat) {
        etatJeu = nouvelEtat;
    }


    //definit l'état du joueur quant au commerce de matières premières avec le voisin de Droite
    @Override
    public void setCommerceMatieresPremieresDroite(boolean nouvelEtat) {
        commerceMatieresPremieresDroite = nouvelEtat;
    }

    //definit l'état du joueur quant au commerce de matières premières avec le voisin de Gauche
    @Override
    public void setCommerceMatieresPremieresGauche(boolean nouvelEtat) {
        commerceMatieresPremieresGauche = nouvelEtat;
    }


    //retourne l'état du joueur quant au commerce de de produits manufactures
    @Override
    public boolean isCommerceProduitsManufactures() {
        return commerceProduitsManufactures;
    }

    //definit l'état du joueur quant au commerce de Produits Manufactures
    @Override
    public void setCommerceProduitsManufactures(boolean nouvelEtat) {
        commerceProduitsManufactures = nouvelEtat;
    }

    // Les pts de Victoire

    //retourne le total de pts de victoire du joueur
    @Override
    public int getPtsVictoire() {
        return ptsVictoire;
    }

    //définit un nv nb de points de victoire. généré par le Generate mais est-ce utile ? (sinon, à suppr de préférence et à la fin du projet)
    @Override
    public void setPtsVictoire(int qte) {
        ptsVictoire = qte;
    }

    //Ajoute n pts de victoire au joueur
    @Override
    public void addPtsVictoire(int n){
        ptsVictoire += n;
    }


    //La science

    //retourne le nb de Tablettes Scientifiques du joueur
    @Override
    public int getNbTablettes() {
        return nbTablettes;
    }

    //retourne le nb de Rouages Scientifiques du joueur
    @Override
    public int getNbRouages() {
        return nbRouages;
    }

    //retourne le nb de Compas Scientifiques du joueur
    @Override
    public int getNbCompas() {
        return nbCompas;
    }

    //Ajoute n tablettes scientifiques au joueur
    @Override
    public void addTablettes(int n){
        nbTablettes += n;
    }

    //Ajoute n rouages scientifiques au joueur
    @Override
    public void addRouages(int n){
        nbRouages += n;
    }

    //Ajoute n compas scientifiques au joueur
    @Override
    public void addCompas(int n){
        nbCompas += n;
    }

    //Ajoute n compas scientifiques au joueur
    @Override
    public void addSymboleUniversel(int n){
        nbSymboleUniversel += n;
    }

    //Les fonctions militaires

    //retourne le nb de symboles rouges (puissance militaire) du joueur
    @Override
    public int getPuissanceMilitaire() {
        return puissanceMilitaire;
    }

    //ajoute n symboles rouges (puissance Militaire) au joueur
    @Override
    public void addPuissanceMilitaire(int n) {
        puissanceMilitaire = puissanceMilitaire+n;
    }

    //retourne le total de pts de victoire militaire
    @Override
    public int getPtsVictoireMilitaire() {
        return ptsVictoireMilitaire;
    }

    //definit le nb de points de victoire militaire. Généré par Generate mais est-ce vraiment utile ?
    @Override
    public void setPtsVictoireMilitaire(int nouveauxPtsVictoireMilitaire) {
        ptsVictoireMilitaire = nouveauxPtsVictoireMilitaire;
    }

    //retourne le nb de jetons de défaite militaire du joueur
    @Override
    public int getNbJetonsDefaite() {
        return nbJetonsDefaite;
    }

    //définit le nb de jetons de défaite militaire du joueur. généré par generate mais est-ce vraiment utile ?
    @Override
    public void setNbJetonsDefaite(int nouveauNbJetonsDefaite) {
        nbJetonsDefaite = nouveauNbJetonsDefaite;
    }

    //ajoute n points de victoire militaire au joueur
    @Override
    public void addPointsVictoireMilitaire(int n){
        ptsVictoireMilitaire += n;
    }

    //ajoute n jetons de défaite militaire au joueur
    @Override
    public void addJetonsDefaiteMilitaire(int n){
        nbJetonsDefaite += n;
    }

    //retire 1 point de victoire militaire au joueur, ce qui correspond à une défaite (dans l,optique, cette fonction sera privilégiée pour le calcul final)
    @Override
    public void retirerPointsVictoireMilitaire(){
        ptsVictoireMilitaire--;
    }

    //retire n jetons de défaite militaire au joueur. Fonction prévue mais potentiellement inutile
    @Override
    public void retirerJetonsDefaiteMilitaire(int n){
        nbJetonsDefaite = nbJetonsDefaite+n;
    }


    //autres fonctions

    @Override
    public void setPuissanceMilitaire(int puissanceMilitaire) {
        this.puissanceMilitaire = puissanceMilitaire;
    }

    @Override
    public void setPieces(int pieces) {
        this.pieces = pieces;
    }


    @Override
    public void setNbTablettes(int nbTablettes) {
        this.nbTablettes = nbTablettes;
    }

    @Override
    public void setNbRouages(int nbRouages) {
        this.nbRouages = nbRouages;
    }

    @Override
    public void setNbCompas(int nbCompas) {
        this.nbCompas = nbCompas;
    }

    @Override
    public Deck getDeck() {
        return deck;
    }

    @Override
    public void setDeck(IDeck deck) {
        this.deck = (Deck) deck;
    }

    @Override
    public List<IJoueur> getAmis() {
        return amis;
    }

    @Override
    public void setAmis(List<IJoueur> amis) {
        this.amis = amis;
    }

    @Override
    public Boolean getAJoue() {
        return AJoue;
    }

    @Override
    public void setAJoue(Boolean AJoue) {
        this.AJoue = AJoue;
    }

    @Override
    public boolean isCommerceMatieresPremieresDroite() {
        return commerceMatieresPremieresDroite;
    }

    @Override
    public boolean isCommerceMatieresPremieresGauche() {
        return commerceMatieresPremieresGauche;
    }

}
