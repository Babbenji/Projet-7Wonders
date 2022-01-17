package modele;


import app.RunServer;
import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import partie.Partie;
import service.ServiceSevenWondersOnline;
import services.exceptions.*;
import type.*;
import user.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

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
    public User inscriptionUser(String nom, String pw) throws PseudoDejaPrisException, RemoteException {
        try {
            return this.serviceSevenWondersOnline.inscriptionUser(nom, pw);
        } catch (RemoteException | PseudoOuMotDePasseIncorrectException e) {
            throw new RuntimeException("RMI Problem");
        } catch (PseudoDejaPrisException ex) {
            throw new PseudoDejaPrisException();
        }
    }

    @Override
    public User connexionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, RemoteException {
        try{
            return this.serviceSevenWondersOnline.connexionUser(nom,pw);
        }catch (PseudoOuMotDePasseIncorrectException | RemoteException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<User> getAmis() throws RemoteException {
        try{
           return this.serviceSevenWondersOnline.getAmis();
        } catch (Exception  e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public User getUserByPseudo(String pseudo) throws RemoteException {
        try{
            return this.serviceSevenWondersOnline.getUserByPseudo(pseudo);
        } catch (Exception e) {
            throw new RemoteException("can't access to RMI");
        }
    }

    @Override
    public IPartie creePartie(User user) throws RemoteException {
        try{
            return this.serviceSevenWondersOnline.creePartie(user);
        } catch (RemoteException e) {
              throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public Partie getPartie(IJoueur joueur) throws RemoteException {
        try{
            return this.serviceSevenWondersOnline.getPartie(joueur);
        } catch (RemoteException e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void inviterUser(int idPartie, User user) throws JoueurNonExistantException, MaxJoueursAtteintException, JoueurDejaAjouteException, RemoteException {
        try{
            this.serviceSevenWondersOnline.inviterUser(idPartie, user);
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
    public void jouerCarte(IJoueur joueur, ICarte carte) throws Exception, RemoteException {
        try{
            this.serviceSevenWondersOnline.jouerCarte(joueur,carte);
        } catch (Exception e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void deffausserCarte(IJoueur joueur, ICarte carte) throws Exception, RemoteException {
        try {
            this.serviceSevenWondersOnline.deffausserCarte(joueur, carte);
        } catch (Exception e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void construireEtape(IJoueur joueur,ICarte carte) throws Exception {
        try {
            this.serviceSevenWondersOnline.construireEtape(joueur,carte);
        } catch (Exception e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public String tableauScore(IJoueur joueur) throws PartieNonTermineeException, RemoteException {
        try {
           return this.serviceSevenWondersOnline.tableauScore(joueur);
        } catch (PartieNonTermineeException | RemoteException e) {
            throw new RuntimeException("RMI Problem");
        }
    }

    @Override
    public void partieTerminee(IJoueur joueur) throws Exception, RemoteException {
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

    @Override
    public List<Partie> getParties() throws RemoteException {
        return this.serviceSevenWondersOnline.getParties();
    }

    @Override
    public Partie getPartieById(int idPartie) throws RemoteException {
        return this.serviceSevenWondersOnline.getPartieById(idPartie);
    }

    @Override
    public Map<User, IJoueur> getAssociationUserJoueur() throws RemoteException {

        return this.serviceSevenWondersOnline.getAssociationUserJoueur();

    }


    @Override
    public Map<User, Partie> getUserDansPreLobby() throws RemoteException {
        return this.serviceSevenWondersOnline.getUserDansPreLobby();
    }

    @Override
    public void setUserDansPreLobby(Map<User, Partie> userDansPreLobby) throws RemoteException {
        this.serviceSevenWondersOnline.setUserDansPreLobby(userDansPreLobby);
    }

}
