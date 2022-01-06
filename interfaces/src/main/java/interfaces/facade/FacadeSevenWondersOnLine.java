package interfaces.facade;

import cartes.Carte;
import interfaces.exceptions.*;
import joueur.Joueur;
import partie.Partie;

import java.rmi.RemoteException;
import java.util.Map;


public interface FacadeSevenWondersOnLine {


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
     *
     */
    void creePartie() throws RemoteException;

    /**
     * Permet de rejoindre une partie
     * @param nomPartie : nom de la partie que le joueur createur va nommer
     * @throws JoueurNonExistantException : Le joueur ajouté n'existe pas
     * @throws MaxJoueursAtteintException : La partie est pleine
     * @throws JoueurDejaAjouteException : Le joueur est déjà dans la partie
     */
    void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException;

    /**
     * TO DO
     * @throws RemoteException
     */
    void miseEnPlaceDuJeu(Joueur joueur) throws RemoteException;

    /**
     *
     * @param joueur
     * @param carte
     */
    void jouerCarte(Joueur joueur, Carte carte) throws Exception;

    /**
     *
     * @param joueur
     * @param carte
     */
    void deffausserCarte (Joueur joueur, Carte carte) throws Exception;


    /**
     * TO DO
     * @param joueur
     */
    void construireEtape(Joueur joueur) throws Exception;


    /**
     * Permet d'avoir le vainqueur de la partie où joueur est impliqué
     * @return : tous les joueurs avec leurs nombre de points
     * @throws PartieNonTermineeException : la partie n'est pas terminée, donc il n'y a pas encore de vainqueur
     */
    String tableauScore(Joueur joueur) throws PartieNonTermineeException;

    /**
     * Permet de savoir si la partie est terminée ou non
     * @param
     * @return
     *  - vrai : la partie est terminée
     *  - faux : la partie n'est pas encore terminée
     */
    void partieTerminee(Joueur joueur) throws Exception;



}