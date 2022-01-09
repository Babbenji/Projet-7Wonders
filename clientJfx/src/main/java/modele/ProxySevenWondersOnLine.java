package modele;


import interfaces.exceptions.JoueurDejaAjouteException;
import interfaces.exceptions.JoueurNonExistantException;
import interfaces.exceptions.MaxJoueursAtteintException;
import interfaces.exceptions.PartieNonTermineeException;
import type.ICarte;
import type.IJoueur;


import java.rmi.RemoteException;

public interface ProxySevenWondersOnLine

{
    void creePartie() throws RemoteException;

    void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException;


    void miseEnPlaceDuJeu(IJoueur joueur) throws RemoteException;


    void jouerCarte(IJoueur joueur, ICarte carte) throws Exception;


    void deffausserCarte (IJoueur joueur, ICarte carte) throws Exception;


    void construireEtape(IJoueur joueur) throws Exception;


    String tableauScore(IJoueur joueur) throws PartieNonTermineeException;

    void partieTerminee(IJoueur joueur) throws Exception;

}
