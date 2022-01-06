package modele;

import cartes.Carte;
import interfaces.exceptions.JoueurDejaAjouteException;
import interfaces.exceptions.JoueurNonExistantException;
import interfaces.exceptions.MaxJoueursAtteintException;
import interfaces.exceptions.PartieNonTermineeException;
import joueur.Joueur;

import java.rmi.RemoteException;

public interface ProxySevenWondersOnLine

{
    void creePartie() throws RemoteException;

    void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException;


    void miseEnPlaceDuJeu(Joueur joueur) throws RemoteException;


    void jouerCarte(Joueur joueur, Carte carte) throws Exception;


    void deffausserCarte (Joueur joueur, Carte carte) throws Exception;


    void construireEtape(Joueur joueur) throws Exception;


    String tableauScore(Joueur joueur) throws PartieNonTermineeException;

    void partieTerminee(Joueur joueur) throws Exception;

}
