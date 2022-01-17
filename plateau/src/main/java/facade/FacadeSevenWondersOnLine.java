package facade;

import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import partie.Partie;
import services.exceptions.*;
import type.*;
import user.User;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface FacadeSevenWondersOnLine {



    public List<IMerveille> recuperationDonnees2();
    public List<ICarte> recuperationDonnees();
    User inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException;

    /**
     * Permet de se connecter à l'application
     * @param nom
     */
    User connexionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException;

    /**
     * Permet d'ajouter un joueur à sa liste d'amis
     * @throws JoueurDejaDansLaListeDAmisException : Le joueur est déjà dans la liste d'amis
     * @throws JoueurNonExistantException : Le joueur ajouté n'existe pas
     * @param nom
     */
    void ajouterJoueurEnAmi(String nom) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException;

    /**
     * Permet de récupérer une liste d'objets User
     */
    List<User> getAmis();

    /**
     * Permet de créer une partie
     *
     */
    IPartie creePartie(User user) throws RemoteException;

    /**
     * Retourne la liste des parties
     * @return partie
     */
    List<Partie> getParties();

    /**
     * Permet de récupérer la partie en fonction d'un ID donné en param
     * @param idPartie
     * @return
     */
    Partie getPartieById(int idPartie);

    /**
     * Permet de rejoindre une partie
     * @param idPartie
     * @param userInvite : user à ajouter à la partie
     * @throws JoueurNonExistantException : Le joueur ajouté n'existe pas
     * @throws MaxJoueursAtteintException : La partie est pleine
     * @throws JoueurDejaAjouteException : Le joueur est déjà dans la partie
     */
    void inviterUser(int idPartie, User userInvite) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException;

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
    void jouerCarte(IJoueur joueur, ICarte carte) throws Exception;

    /**
     *
     * @param joueur
     * @param carte
     */
    void deffausserCarte (IJoueur joueur, ICarte carte) throws Exception;


    /**
     * TO DO
     * @param joueur
     */
    void construireEtape(IJoueur joueur, ICarte carte) throws Exception;


    /**
     * Permet d'avoir le vainqueur de la partie où joueur est impliqué
     * @return : tous les joueurs avec leurs nombre de points
     * @throws PartieNonTermineeException : la partie n'est pas terminée, donc il n'y a pas encore de vainqueur
     */
    String tableauScore(IJoueur joueur) throws PartieNonTermineeException;

    /**
     * Permet de savoir si la partie est terminée ou non
     * @param
     * @return
     *  - vrai : la partie est terminée
     *  - faux : la partie n'est pas encore terminée
     */
    void partieTerminee(IJoueur joueur) throws Exception;

    /**
     *
     * @param pseudo
     * @return
     */
    User getUserByPseudo(String pseudo) throws RemoteException;

    Partie getPartie(IJoueur joueur);
    Map<User, IJoueur> getAssociationUserJoueur();
    Map<IJoueur, Partie> getAssociationJoueurPartie();
    Map<User, Partie> getUserDansPreLobby();
    void setUserDansPreLobby(Map<User, Partie> userDansPreLobby);
}