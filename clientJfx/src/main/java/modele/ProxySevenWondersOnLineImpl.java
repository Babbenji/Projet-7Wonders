package modele;


import app.RunServer;
import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import service.ServiceSevenWondersOnline;
import services.exceptions.*;
import type.*;
import user.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
    public User inscriptionUser(String nom, String pw) throws PseudoDejaPrisException {
        try {
            return this.serviceSevenWondersOnline.inscriptionUser(nom, pw);
        } catch (RemoteException | PseudoOuMotDePasseIncorrectException e) {
            throw new RuntimeException("RMI Problem");
        } catch (PseudoDejaPrisException ex) {
            throw new PseudoDejaPrisException();
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
    public IPartie creePartie() throws RemoteException {
        try{
            return this.serviceSevenWondersOnline.creePartie();
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

    @Override
    public void ajouterJoueurEnAmi(String pseudoAmi) throws JoueurNonExistantException, JoueurDejaDansLaListeDAmisException, RemoteException {
        try {
            this.serviceSevenWondersOnline.ajouterJoueurEnAmi(pseudoAmi);
        } catch (JoueurDejaDansLaListeDAmisException | JoueurNonExistantException | RemoteException e) {
            e.printStackTrace();
        }

    }
}
