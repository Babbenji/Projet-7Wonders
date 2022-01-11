package service;

import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import services.exceptions.*;
import type.ICarte;
import type.IJoueur;
import user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServiceSevenWondersOnline extends Remote
{

    /**
     * Permet de s'inscrire à l'application
     * @param nom
     */
    User inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException,RemoteException;

    /**
     * Permet de se connecter à l'application
     * @param nom
     */
    User connexionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException,RemoteException;

    /**
     * Permet d'ajouter un joueur à sa liste d'amis
     * @throws JoueurDejaDansLaListeDAmisException : Le joueur est déjà dans la liste d'amis
     * @throws JoueurNonExistantException : Le joueur ajouté n'existe pas
     * @param nom
     */
    void ajouterJoueurEnAmi(String nom) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException,RemoteException;

    /**
     * Permet de récupérer une liste d'objets User
     */
    void getAmis()throws RemoteException;

    /**
     * Permet d'inviter un joueur
     * @param joueur
     */
    void inviterJoueur(IJoueur joueur)throws RemoteException;

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
    void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException, RemoteException;

    /**
     * TO DO
     * @throws RemoteException
     */
    void miseEnPlaceDuJeu(IJoueur joueur) throws RemoteException;

    /**
     *
     * @param joueur
     * @param carte
     */
    void jouerCarte(IJoueur joueur, ICarte carte) throws Exception, RemoteException;

    /**
     *
     * @param joueur
     * @param carte
     */
    void deffausserCarte (IJoueur joueur, ICarte carte) throws Exception, RemoteException;


    /**
     * TO DO
     * @param joueur
     */
    void construireEtape(IJoueur joueur) throws Exception, RemoteException;


    /**
     * Permet d'avoir le vainqueur de la partie où joueur est impliqué
     * @return : tous les joueurs avec leurs nombre de points
     * @throws PartieNonTermineeException : la partie n'est pas terminée, donc il n'y a pas encore de vainqueur
     */
    String tableauScore(IJoueur joueur) throws PartieNonTermineeException, RemoteException;

    /**
     * Permet de savoir si la partie est terminée ou non
     * @param
     * @return
     *  - vrai : la partie est terminée
     *  - faux : la partie n'est pas encore terminée
     */
    void partieTerminee(IJoueur joueur) throws Exception, RemoteException;

}
