package service;
import cartes.Carte;
import interfaces.exceptions.JoueurDejaAjouteException;
import interfaces.exceptions.JoueurNonExistantException;
import interfaces.exceptions.MaxJoueursAtteintException;
import joueur.Joueur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ServiceSevenWondersOnline {
    void creePartie() throws RemoteException;
    void rejoindreUnePartie(String nom) throws RemoteException, MaxJoueursAtteintException, JoueurNonExistantException, JoueurDejaAjouteException;

    void miseEnPlacePartie() throws RemoteException;
    void jouerCarte(Joueur joueur, Carte carte) throws Exception;
    void deffausserCarte (Joueur joueur, Carte carte) throws Exception;
    void construireEtape(Joueur joueur) throws Exception;

    void partieTerminee(Joueur joueur) throws RemoteException;
    String tableauScore();

}
