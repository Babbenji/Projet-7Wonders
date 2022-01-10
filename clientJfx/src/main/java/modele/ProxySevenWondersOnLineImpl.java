package modele;


import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import service.ServiceSevenWondersOnline;
import services.exceptions.*;
import type.ICarte;
import type.IJoueur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ProxySevenWondersOnLineImpl  implements ProxySevenWondersOnLine
{
    ServiceSevenWondersOnline serviceSevenWondersOnline;

    public ProxySevenWondersOnLineImpl() throws RemoteException, NotBoundException, MalformedURLException {
        System.out.println("Lancement du client");
       try{
           this.serviceSevenWondersOnline = (ServiceSevenWondersOnline) Naming.lookup(ServiceSevenWondersOnline.RMI_SERVEUR);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
           e.printStackTrace();
       }
    }

    @Override
    public void inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException {
        try {
            this.serviceSevenWondersOnline.inscriptionUser(nom, pw);
        } catch (PseudoOuMotDePasseIncorrectException | PseudoDejaPrisException e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void connexionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException {
        try{
            this.serviceSevenWondersOnline.connexionUser(nom,pw);
        }catch (PseudoOuMotDePasseIncorrectException e){
            throw new RuntimeException("RMI Problem");

        }

    }

    @Override
    public void ajouterJoueurEnAmi(String nom) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException {
        try{
            this.serviceSevenWondersOnline.ajouterJoueurEnAmi(nom);
        } catch (JoueurDejaDansLaListeDAmisException | JoueurNonExistantException e) {
            throw new RuntimeException("RMI Problem");

        }
    }

    @Override
    public void getAmis() {
        try{
            this.serviceSevenWondersOnline.getAmis();
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
        } catch (JoueurNonExistantException | MaxJoueursAtteintException | JoueurDejaAjouteException e) {
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
        } catch (PartieNonTermineeException e) {
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
