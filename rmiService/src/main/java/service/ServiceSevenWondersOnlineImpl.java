package service;

import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import facade.FacadeSevenWondersOnLine;
import facade.FacadeSevenWondersOnlineImpl;
import services.exceptions.*;
import type.ICarte;
import type.IJoueur;


import java.rmi.RemoteException;

public class ServiceSevenWondersOnlineImpl  implements ServiceSevenWondersOnline {

    FacadeSevenWondersOnLine facadeSevenWondersOnLine;

    public ServiceSevenWondersOnlineImpl() throws RemoteException
    {
        super();
        this.facadeSevenWondersOnLine =  new FacadeSevenWondersOnlineImpl();
    }

    public static ServiceSevenWondersOnline creer() throws RemoteException{
        return  new ServiceSevenWondersOnlineImpl();
    }


    @Override
    public void inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException {
        this.facadeSevenWondersOnLine.inscriptionUser(nom,pw);
    }

    @Override
    public void connexionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException {
        this.facadeSevenWondersOnLine.connexionUser(nom, pw);

    }

    @Override
    public void ajouterJoueurEnAmi(String nom) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException {
        this.facadeSevenWondersOnLine.ajouterJoueurEnAmi(nom);

    }

    @Override
    public void getAmis() {
        this.facadeSevenWondersOnLine.getAmis();

    }

    @Override
    public void inviterJoueur(IJoueur joueur) {
        this.facadeSevenWondersOnLine.inviterJoueur(joueur);

    }

    @Override
    public void creePartie() throws RemoteException {
        this.facadeSevenWondersOnLine.creePartie();

    }

    @Override
    public void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException {
        this.facadeSevenWondersOnLine.rejoindreUnePartie(nomPartie);
    }

    @Override
    public void miseEnPlaceDuJeu(IJoueur joueur) throws RemoteException {
        this.facadeSevenWondersOnLine.miseEnPlaceDuJeu(joueur);

    }

    @Override
    public void jouerCarte(IJoueur joueur, ICarte carte) throws Exception {
        this.facadeSevenWondersOnLine.jouerCarte(joueur,carte);

    }

    @Override
    public void deffausserCarte(IJoueur joueur, ICarte carte) throws Exception {
        this.facadeSevenWondersOnLine.deffausserCarte(joueur, carte);
    }

    @Override
    public void construireEtape(IJoueur joueur) throws Exception {
        this.facadeSevenWondersOnLine.construireEtape(joueur);

    }

    @Override
    public String tableauScore(IJoueur joueur) throws PartieNonTermineeException {
       return this.facadeSevenWondersOnLine.tableauScore(joueur);
    }

    @Override
    public void partieTerminee(IJoueur joueur) throws Exception {
        this.facadeSevenWondersOnLine.partieTerminee(joueur);

    }
}
