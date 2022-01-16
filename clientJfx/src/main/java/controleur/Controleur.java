package controleur;

import exceptions.MaxJoueursAtteintException;
import facade.FacadeSevenWondersOnlineImpl;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import services.exceptions.JoueurDejaDansLaListeDAmisException;
import services.exceptions.JoueurNonExistantException;
import services.exceptions.*;
import type.*;
import type.IDeck;
import type.IJoueur;
import type.IMerveille;
import javafx.stage.Stage;
import modele.ProxySevenWondersOnLine;
import modele.ProxySevenWondersOnLineImpl;
import user.User;
import vues.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class Controleur
{
    private ProxySevenWondersOnLine facade;

    private VueMenuNonConnecte vueMenuNonConnecte;
    private VueConnexion vueConnexion;
    private VueInscription vueInscription;
    private VueMenuConnecte vueMenuConnecte;
    private VuePartie vuePartie;
    private VueWaitingRoom vueWaitingRoom;
    private int NB_JOUEURS = 4;

    private IJoueur joueur;
    private IDeck deck;
    private IMerveille merveille;
    private String nom;
    private User user;
    private static int nbUserWaitingRoom;
    FacadeSevenWondersOnlineImpl facadeta = new FacadeSevenWondersOnlineImpl();
    IPartie partie = facadeta.creePartie(
            //this.user
            new User("test")
    );


    public Controleur(Stage stage) throws IOException, NotBoundException {
        facade = new ProxySevenWondersOnLineImpl();

        vueMenuNonConnecte = VueMenuNonConnecte.creerVue(stage);
        vueMenuNonConnecte.initialiserControleur(this);
        vueConnexion = VueConnexion.creerVue(stage);
        vueConnexion.initialiserControleur(this);
        vueInscription = VueInscription.creerVue(stage);
        vueInscription.initialiserControleur(this);
        vueMenuConnecte = VueMenuConnecte.creer(stage);
        vueMenuConnecte.initialiserControleur(this);
        vuePartie = VuePartie.creerVue(stage,this);
        vueWaitingRoom = VueWaitingRoom.creerVue(stage);
        vueWaitingRoom.initialiserControleur(this);
    }

    public void run() throws RemoteException {
        vueMenuNonConnecte.show();
    }

    public void miseEnPlaceDuJeu() throws RemoteException {


//        IJoueur joueur1 = new Joueur("Aziz");
//        ArrayList<IJoueur> lj = new ArrayList<>();
//        lj.add(joueur1);
//        List<ICarte> lc = facade.getLesCartes();
//        List<IMerveille> lm = facade.getLesMerveilles();

        partie.miseEnPlacePartie();
        joueur = partie.getListeDesJoueurs().get(0);
        this.merveille = partie.getListeDesJoueurs().get(0).getMerveille();
        this.deck = partie.getListeDesJoueurs().get(0).getDeck();
        System.out.println(this.deck.getCarteDansDeck(0));
    }

    public ProxySevenWondersOnLine getFacade() {
        return facade;
    }

    public IJoueur getJoueur() {
        return joueur;
    }

    public IDeck getDeck() throws RemoteException
    {
        return deck ;
    }

    public IMerveille getMerveille() throws RemoteException {
        return this.merveille;
    }

    public void goToMenu() {
        this.vueMenuNonConnecte.show();
    }

    public void goToMenuConnecte()
    {
        vueMenuConnecte.chargerDonnees();
        this.vueMenuConnecte.show();
    }

    public void goToConnexion()  {
        this.vueConnexion.show();
    }

    public void goToInscription() {
        this.vueInscription.show();
    }

    public void goToWaitingRoom() {
        vueWaitingRoom.chargerDonnees();
        this.vueWaitingRoom.show();
    }

    public String getNom() {
        return this.nom;
    }

    public void goToPartie() throws RemoteException {
        vuePartie.chargerDonnees();
        this.vuePartie.show();
    }

    public Boolean connexion(String pseudo,String mdp) throws PseudoOuMotDePasseIncorrectException, RemoteException, NotBoundException, MalformedURLException {
        boolean connexionReussie = false;
        this.user = this.facade.connexionUser(pseudo,mdp);
        if(!Objects.isNull(this.user))
        {
            connexionReussie = true;
            this.nom = pseudo;
            this.goToMenuConnecte();
        }
        return connexionReussie;
    }

    public Boolean inscription(String pseudo,String mdp) throws PseudoDejaPrisException, RemoteException, NotBoundException, MalformedURLException {
        boolean inscriptionReussie = false;
        this.user = this.facade.inscriptionUser(pseudo,mdp);
        if(!Objects.isNull(this.user)){
            inscriptionReussie = true;
        }
        return inscriptionReussie;
    }

    public List<User> getAmis() {
        return this.user.getAmis();
    }
    public void exit() {
        System.exit(0);
    }

    public void addFriend(User user, String pseudoAmi){
        try{
            this.facade.ajouterJoueurEnAmi( pseudoAmi);
        } catch (RemoteException | JoueurDejaDansLaListeDAmisException | JoueurNonExistantException e) {
            e.printStackTrace();
        }


    }

    public void inviterUser(int idPartie, User user) throws JoueurDejaAjouteException, MaxJoueursAtteintException, JoueurNonExistantException {
        try {
            this.facade.inviterUser(idPartie, user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void construireEtape() throws Exception {
        ICarte carte = vuePartie.getCarte();
        partie.construireEtape(joueur, carte);
        vuePartie.affichageInteractifDesVariables();
        System.out.println("carte construite");
    }

    public void defausserCarte() throws Exception {
        ICarte carte = vuePartie.getCarte();
        partie.deffausserCarte(joueur, carte);
        vuePartie.affichageInteractifDesVariables();
        System.out.println("carte defausser");
    }



    public void jouerCarte() throws Exception
    {
        ICarte carte = vuePartie.getCarte();
        partie.jouerCarte(joueur, carte);
        vuePartie.affichageInteractifDesVariables();
        System.out.println("carte joue");


    }


    public User getUser() {
        return this.user;
    }

    public IJoueur getInfoJDroite() {
        int indice = partie.getListeDesJoueurs().indexOf(this.joueur);
        return partie.getListeDesJoueurs().get(voisinDeDroite(indice));
    }

    public IJoueur getInfoJGauche() {
        int indice = partie.getListeDesJoueurs().indexOf(this.joueur);
        return partie.getListeDesJoueurs().get(voisinDeGauche(indice));
    }


    public int voisinDeDroite(int indice)
    {
        if (indice == NB_JOUEURS-1)
            return 0;
        return indice+1;
    }

    public int voisinDeGauche(int indice)
    {
        if (indice == 0)
            return NB_JOUEURS - 1;
        return indice-1;
    }

    public int joueurFace(int indice)
    {
        int indiceARetourner;
        if (indice == 0) {

            indiceARetourner = 2;
        }
        else if(indice == 1 ){
            indiceARetourner = 3;
        }
        else if(indice == 2){
            indiceARetourner = 0;
        }
        else {
            indiceARetourner = 1;
        }
        return indiceARetourner;
    }

    public IJoueur getInfoJFace() {
        int indice = partie.getListeDesJoueurs().indexOf(this.joueur);
        return partie.getListeDesJoueurs().get(joueurFace(indice));
    }

    public int ajoutUserWaitingRoom() {
       return nbUserWaitingRoom+1;
    }
}
