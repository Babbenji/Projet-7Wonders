package service;

import cartes.Carte;
import facade.FacadeSevenWondersOnlineImpl;
import interfaces.exceptions.JoueurDejaAjouteException;
import interfaces.exceptions.JoueurNonExistantException;
import interfaces.exceptions.MaxJoueursAtteintException;
import interfaces.facade.FacadeSevenWondersOnLine;
import joueur.Joueur;

import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Map;

public class ServiceSevenWondersOnlineImpl extends UnicastRemoteObject implements ServiceSevenWondersOnline {

    FacadeSevenWondersOnLine facadeSevenWondersOnLine;

    protected ServiceSevenWondersOnlineImpl() throws RemoteException {
        super();
        this.facadeSevenWondersOnLine = new FacadeSevenWondersOnlineImpl(); // a faire dans le Modele
    }

    protected ServiceSevenWondersOnlineImpl(int port) throws RemoteException {
        super(port);
    }

    protected ServiceSevenWondersOnlineImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    public static ServiceSevenWondersOnline creer() throws RemoteException{
        return  new ServiceSevenWondersOnlineImpl();
    }

    @Override
    public void creePartie() throws RemoteException {
        this.facadeSevenWondersOnLine.creePartie();
    }

    @Override
    public void rejoindreUnePartie(String nom) throws RemoteException, MaxJoueursAtteintException, JoueurNonExistantException, JoueurDejaAjouteException {
        this.facadeSevenWondersOnLine.rejoindreUnePartie(nom);

    }

    @Override
    public void miseEnPlacePartie() throws RemoteException {

        //this.facadeSevenWondersOnLine.miseEnPlacePartie();


    }

    @Override
    public void jouerCarte(Joueur joueur, Carte carte) throws Exception {
        this.facadeSevenWondersOnLine.jouerCarte(joueur,carte);


    }

    @Override
    public void deffausserCarte(Joueur joueur, Carte carte) throws Exception {
        this.facadeSevenWondersOnLine.deffausserCarte(joueur,carte);


    }

    @Override
    public void construireEtape(Joueur joueur) throws Exception {
        this.facadeSevenWondersOnLine.construireEtape(joueur);


    }

    @Override
    public void partieTerminee(Joueur joueur) throws RemoteException {
//        this.facadeSevenWondersOnLine.partieTerminee(joueur);



    }

    @Override
    public String tableauScore() {
        return null;
    }

}
