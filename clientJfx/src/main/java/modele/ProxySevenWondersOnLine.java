package modele;


import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import services.exceptions.JoueurDejaAjouteException;
import services.exceptions.JoueurNonExistantException;
import type.ICarte;
import type.IJoueur;


import java.rmi.RemoteException;

public interface ProxySevenWondersOnLine

{
    void creePartie() throws RemoteException;

    void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException, MaxJoueursAtteintException, JoueurDejaAjouteException;


    void miseEnPlaceDuJeu(IJoueur joueur) throws RemoteException;


    void jouerCarte(IJoueur joueur, ICarte carte) throws Exception;


    void deffausserCarte (IJoueur joueur, ICarte carte) throws Exception;


    void construireEtape(IJoueur joueur) throws Exception;


    String tableauScore(IJoueur joueur) throws PartieNonTermineeException, PartieNonTermineeException;

    void partieTerminee(IJoueur joueur) throws Exception;

}
