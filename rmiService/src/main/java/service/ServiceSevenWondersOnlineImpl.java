package service;

import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import facade.FacadeSevenWondersOnLine;
import facade.FacadeSevenWondersOnlineImpl;
import services.exceptions.*;
import type.*;
import user.User;


import java.rmi.RemoteException;
import java.util.List;

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
    public List<ICarte> recuperationDonnees() throws RemoteException {
        return this.facadeSevenWondersOnLine.recuperationDonnees();
    }

    @Override
    public List<IMerveille> recuperationDonnees2() throws RemoteException {
        return this.facadeSevenWondersOnLine.recuperationDonnees2();
    }

    @Override
    public User inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException {
        return this.facadeSevenWondersOnLine.inscriptionUser(nom,pw);
    }

    @Override
    public User connexionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException {
        System.out.println("ici");
        return this.facadeSevenWondersOnLine.connexionUser(nom, pw);

    }

    @Override
    public void ajouterJoueurEnAmi(String nom) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException {
        this.facadeSevenWondersOnLine.ajouterJoueurEnAmi(nom);

    }

    @Override
    public List<User> getAmis() {
        return this.facadeSevenWondersOnLine.getAmis();

    }

    @Override
    public void inviterJoueur(IJoueur joueur) {
        this.facadeSevenWondersOnLine.inviterJoueur(joueur);

    }

    @Override
    public IPartie creePartie() throws RemoteException {
        return this.facadeSevenWondersOnLine.creePartie();

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
    public void construireEtape(IJoueur joueur,ICarte carte) throws Exception {
        this.facadeSevenWondersOnLine.construireEtape(joueur,carte);

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
