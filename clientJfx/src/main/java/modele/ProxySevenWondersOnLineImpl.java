package modele;


import app.RunServer;
import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import service.ServiceSevenWondersOnline;
import service.ServiceSevenWondersOnlineImpl;
import services.exceptions.*;
import type.ICarte;
import type.IJoueur;
import user.User;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ProxySevenWondersOnLineImpl  implements ProxySevenWondersOnLine
{
    ServiceSevenWondersOnline serviceSevenWondersOnline;



    public ProxySevenWondersOnLineImpl() throws RemoteException, NotBoundException, MalformedURLException {
        System.out.println("Lancement du client");

       try{

           this.serviceSevenWondersOnline = (ServiceSevenWondersOnline) Naming.lookup(RunServer.RMI_SERVER);
        } catch (RemoteException | NotBoundException e) {
           e.printStackTrace();
       }
        System.out.println("Ok");

    }


    @Override
    public void inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException {
        try {
            this.serviceSevenWondersOnline.inscriptionUser(nom, pw);
        } catch (PseudoOuMotDePasseIncorrectException | PseudoDejaPrisException | RemoteException e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public User connexionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException {
        try{
            return this.serviceSevenWondersOnline.connexionUser(nom,pw);
        }catch (PseudoOuMotDePasseIncorrectException | RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void ajouterJoueurEnAmi(String nom) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException {
        try{
            this.serviceSevenWondersOnline.ajouterJoueurEnAmi(nom);
        } catch (JoueurDejaDansLaListeDAmisException | JoueurNonExistantException | RemoteException e) {
            throw new RuntimeException("RMI Problem");

        }
    }

    @Override
    public List<User> getAmis() {
        try{
           return this.serviceSevenWondersOnline.getAmis();
        } catch (Exception  e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void inviterJoueur(IJoueur joueur) {
        try{
            this.serviceSevenWondersOnline.inviterJoueur(joueur);
        } catch (Exception  e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void creePartie() throws RemoteException {
        try{
            this.serviceSevenWondersOnline.creePartie();
        } catch (RemoteException e) {
              throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void rejoindreUnePartie(String nomPartie) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException {
        try{
            this.serviceSevenWondersOnline.rejoindreUnePartie(nomPartie);
        } catch (JoueurNonExistantException | MaxJoueursAtteintException | JoueurDejaAjouteException | RemoteException e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void miseEnPlaceDuJeu(IJoueur joueur) throws RemoteException {
        try{
            this.serviceSevenWondersOnline.miseEnPlaceDuJeu(joueur);
        } catch (RemoteException e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void jouerCarte(IJoueur joueur, ICarte carte) throws Exception {
        try{
            this.serviceSevenWondersOnline.jouerCarte(joueur,carte);
        } catch (Exception e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void deffausserCarte(IJoueur joueur, ICarte carte) throws Exception {
        try {
            this.serviceSevenWondersOnline.deffausserCarte(joueur, carte);
        } catch (Exception e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void construireEtape(IJoueur joueur) throws Exception {
        try {
            this.serviceSevenWondersOnline.construireEtape(joueur);
        } catch (Exception e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public String tableauScore(IJoueur joueur) throws PartieNonTermineeException {
        try {
           return this.serviceSevenWondersOnline.tableauScore(joueur);
        } catch (PartieNonTermineeException | RemoteException e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void partieTerminee(IJoueur joueur) throws Exception {
        try {
            this.serviceSevenWondersOnline.partieTerminee(joueur);
        } catch (Exception e) {
            throw new RuntimeException("RMI Problem");
        }
    }
}
