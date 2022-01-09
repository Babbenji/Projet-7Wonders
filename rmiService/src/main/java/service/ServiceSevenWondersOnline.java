package service;

import exceptions.MaxJoueursAtteintException;
import services.exceptions.JoueurDejaAjouteException;
import services.exceptions.JoueurNonExistantException;
import type.ICarte;
import type.IJoueur;

import java.rmi.RemoteException;


public interface ServiceSevenWondersOnline
{
    void creePartie() throws RemoteException;
    void rejoindreUnePartie(String nom) throws RemoteException, MaxJoueursAtteintException, JoueurNonExistantException, JoueurDejaAjouteException;

    void miseEnPlacePartie(IJoueur joueur) throws RemoteException;
    void jouerCarte(IJoueur joueur, ICarte carte) throws Exception;
    void deffausserCarte (IJoueur joueur, ICarte carte) throws Exception;
    void construireEtape(IJoueur joueur) throws Exception;

    void partieTerminee(IJoueur joueur) throws Exception;
    String tableauScore();

}
