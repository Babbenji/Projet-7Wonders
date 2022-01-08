package service;

import facade.FacadeSevenWondersOnlineImpl;
import interfaces.exceptions.JoueurDejaAjouteException;
import interfaces.exceptions.JoueurNonExistantException;
import interfaces.exceptions.MaxJoueursAtteintException;
import interfaces.facade.FacadeSevenWondersOnLine;
import interfaces.type.ICarte;
import interfaces.type.IJoueur;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ServiceSevenWondersOnlineImpl extends UnicastRemoteObject implements ServiceSevenWondersOnline {

    FacadeSevenWondersOnLine facadeSevenWondersOnLine;

    protected ServiceSevenWondersOnlineImpl() throws RemoteException
    {
        super();
        this.facadeSevenWondersOnLine =  new FacadeSevenWondersOnlineImpl();
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
    public void miseEnPlacePartie(IJoueur joueur) throws RemoteException {

        this.facadeSevenWondersOnLine.miseEnPlaceDuJeu(joueur);


    }

    @Override
    public void jouerCarte(IJoueur joueur, ICarte carte) throws Exception {
        this.facadeSevenWondersOnLine.jouerCarte(joueur,carte);


    }

    @Override
    public void deffausserCarte(IJoueur joueur, ICarte carte) throws Exception {
        this.facadeSevenWondersOnLine.deffausserCarte(joueur,carte);


    }

    @Override
    public void construireEtape(IJoueur joueur) throws Exception {
        this.facadeSevenWondersOnLine.construireEtape(joueur);


    }

    @Override
    public void partieTerminee(IJoueur joueur) throws Exception, RemoteException {
        this.facadeSevenWondersOnLine.partieTerminee(joueur);

    }

    @Override
    public String tableauScore() {
        return null;
    }

}
