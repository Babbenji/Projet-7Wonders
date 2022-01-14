package modele;


import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import services.exceptions.*;
import type.*;
import user.User;


import java.rmi.RemoteException;
import java.util.List;

public interface ProxySevenWondersOnLine

{
    /**
     * Permet de s'inscrire à l'application
     * @param nom  pseudo choisi
     * @param pw   mot de passe choisi
     */
    User inscriptionUser(String nom, String pw) throws PseudoDejaPrisException;

    /**
     * Permet de se connecter à l'application
     * @param nom  pseudo renseigné
     * @param pw  mot de passe renseigné
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
     * Permet d'inviter un joueur
     * @param joueur
     */
    void inviterJoueur(IJoueur joueur);

    IMerveille getMerveille(IJoueur joueur) throws RemoteException;
    IDeck getDeck (IJoueur joueur) throws RemoteException;

    /**
     * Permet de créer une partie
     *
     */
    IPartie creePartie() throws RemoteException;

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
    void construireEtape(IJoueur joueur) throws Exception;


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

}
