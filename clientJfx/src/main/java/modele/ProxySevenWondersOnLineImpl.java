package modele;

import cartes.Carte;
import interfaces.exceptions.JoueurDejaAjouteException;
import interfaces.exceptions.JoueurNonExistantException;
import interfaces.exceptions.MaxJoueursAtteintException;
import interfaces.exceptions.PartieNonTermineeException;
import joueur.Joueur;

import java.rmi.RemoteException;

public class ProxySevenWondersOnLineImpl  implements ProxySevenWondersOnLine
{


    @Override
    public void creePartie() throws RemoteException
    {




    }

    @Override
    public void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException {

    }

    @Override
    public void miseEnPlaceDuJeu(Joueur joueur) throws RemoteException
    {

    }

    @Override
    public void jouerCarte(Joueur joueur, Carte carte) throws Exception
    {

    }

    @Override
    public void deffausserCarte(Joueur joueur, Carte carte) throws Exception
    {

    }

    @Override
    public void construireEtape(Joueur joueur) throws Exception
    {

    }

    @Override
    public String tableauScore(Joueur joueur) throws PartieNonTermineeException
    {
        return null;
    }

    @Override
    public void partieTerminee(Joueur joueur) throws Exception
    {

    }
}
