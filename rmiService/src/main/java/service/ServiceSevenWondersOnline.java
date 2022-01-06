package service;


import interfaces.exceptions.JoueurDejaAjouteException;
import interfaces.exceptions.JoueurNonExistantException;
import interfaces.exceptions.MaxJoueursAtteintException;
import interfaces.type.ICarte;
import interfaces.type.IJoueur;
import java.rmi.RemoteException;


public interface ServiceSevenWondersOnline
{
    void creePartie() throws RemoteException;
    void rejoindreUnePartie(String nom) throws RemoteException, MaxJoueursAtteintException, JoueurNonExistantException, JoueurDejaAjouteException;

    void miseEnPlacePartie() throws RemoteException;
    void jouerCarte(IJoueur joueur, ICarte carte) throws Exception;
    void deffausserCarte (IJoueur joueur, ICarte carte) throws Exception;
    void construireEtape(IJoueur joueur) throws Exception;

    void partieTerminee(IJoueur joueur) throws RemoteException;
    String tableauScore();

}
