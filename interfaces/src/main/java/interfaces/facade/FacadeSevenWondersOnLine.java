package interfaces.facade;

import interfaces.exceptions.*;
import joueur.Joueur;

import java.rmi.RemoteException;
import java.util.Map;


public interface FacadeSevenWondersOnLine {

    Map<String,Integer> listesDesRessourcesDesVoisinsDeChaqueJoueur(int positionJoueur);

    /**
     * Permet de s'inscrire à l'application
     * @param nom
     */
    void inscriptionUser(String nom);//pr l'instant pr que ce soit plus simple

    /**
     * Permet de se connecter à l'application
     * @param nom
     */
    void connexionUser(String nom);//pr l'instant pr que ce soit plus simple

    /**
     * Permet d'ajouter un joueur à sa liste d'amis
     * @throws JoueurDejaDansLaListeDAmisException : Le joueur est déjà dans la liste d'amis
     * @throws JoueurNonExistantException : Le joueur ajouté n'existe pas
     * @param nom
     */
    void ajouterJoueurEnAmi(String nom) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException;

    /**
     * Permet d'inviter un joueur
     * @param joueur
     */
    void inviterJoueur(Joueur joueur);

    /**
     * Permet de créer une partie
     * @param joueur : nom du créateur
     * @return token à partager avec la personne que l'on veut inviter
     */
    String creePartie(String joueur) throws RemoteException;

    /**
     * Permet de rejoindre une partie
     * @param nom : nom du joueur qui veut rejoindre la partie
     * @throws JoueurNonExistantException : Le joueur ajouté n'existe pas
     * @throws MaxJoueursAtteintException : La partie est pleine
     * @throws JoueurDejaAjouteException : Le joueur est déjà dans la partie
     */
    void rejoindreUnePartie(String nom) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException;

    /**
     * TO DO
     * @throws RemoteException
     */
    void miseEnPlaceDuJeu() throws RemoteException;

    /**
     * TO DO
     */
    void constructionDesListes();

    /**
     * TO DO
     */
    void joueursPret();

    /**
     * TO DO
     */
    void finAge();

    /**
     * TO DO
     * @param joueur
     * @param choix
     * @throws RemoteException
     */
    void choixCarteAJouee(String joueur, String choix) throws RemoteException;

    /**
     * TO DO
     * @param positionJoueur
     * @param choix
     */
    void jouerCarteCommerce(int positionJoueur, String choix);

    /**
     * TO DO
     * @param postionCarte
     * @param choix
     */
    void defausserCarte (int postionCarte, String choix);

    /**
     * TO DO
     * @param positionJoueur
     * @param choix
     */
    void debloquerUneEtapeMerveille(int positionJoueur, String choix);

    /**
     * TO DO
     */
    void passerAgeSuivant();

    /**
     * TO DO
     */
    void passerTourSuivant();

    /**
     * TO DO
     */
    void batailleMilitaire();

    /**
     * TO DO
     */
    void nombreDePointsMilitaireAGagnerSelonAge();

    /**
     * Permet de savoir si la partie est terminée ou non
     * @param
     * @return
     *  - vrai : la partie est terminée
     *  - faux : la partie n'est pas encore terminée
     */
    void partieTerminee();

    /**
     * Permet d'avoir le vainqueur de la partie où joueur est impliqué
     * @param joueur
     * @return : le pseudo du vainqueur
     * @throws PartieNonTermineeException : la partie n'est pas terminée, donc il n'y a pas encore de vainqueur
     */
    String getVainqueur(String joueur) throws PartieNonTermineeException;

    /**
     * La partie est terminée, les joueurs quittent la partie
     * @param
     */
    void finDePartie();

}