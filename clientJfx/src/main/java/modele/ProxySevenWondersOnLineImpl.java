package modele;


import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import services.exceptions.JoueurDejaAjouteException;
import services.exceptions.JoueurNonExistantException;
import type.ICarte;
import type.IJoueur;

import java.rmi.RemoteException;

public class ProxySevenWondersOnLineImpl  implements ProxySevenWondersOnLine
{

    @Override
    public void creePartie() throws RemoteException {

    }

    @Override
    public void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException {

    }

    @Override
    public void miseEnPlaceDuJeu(IJoueur joueur) throws RemoteException {

    }

    @Override
    public void jouerCarte(IJoueur joueur, ICarte carte) throws Exception {

    }

    @Override
    public void deffausserCarte(IJoueur joueur, ICarte carte) throws Exception {

    }

    @Override
    public void construireEtape(IJoueur joueur) throws Exception {

    }

    @Override
    public String tableauScore(IJoueur joueur) throws PartieNonTermineeException {
        return null;
    }

    @Override
    public void partieTerminee(IJoueur joueur) throws Exception {

    }
}
