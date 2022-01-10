package modele;


import exceptions.MaxJoueursAtteintException;
import exceptions.PartieNonTermineeException;
import service.ServiceSevenWondersOnline;
import services.exceptions.*;
import type.ICarte;
import type.IJoueur;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ProxySevenWondersOnLineImpl  implements ProxySevenWondersOnLine
{
    ServiceSevenWondersOnline serviceSevenWondersOnline;

    public ProxySevenWondersOnLineImpl() {
        System.out.println("Lancement du client");
       /* try{
           this.serviceSevenWondersOnline = (ServiceSevenWondersOnline) Naming.lookup(ServiceSevenWondersOnline.RMI_SERVEUR);
        }*/
    }

    @Override
    public void inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException {

    }

    @Override
    public void connexionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException {

    }

    @Override
    public void ajouterJoueurEnAmi(String nom) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException {

    }

    @Override
    public void getAmis() {

    }

    @Override
    public void inviterJoueur(IJoueur joueur) {

    }

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
