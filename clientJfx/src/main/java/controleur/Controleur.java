package controleur;

import exceptions.MaxJoueursAtteintException;
import facade.FacadeSevenWondersOnlineImpl;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import joueur.Joueur;
import merveilles.exceptions.MaximumEtapeAtteintException;
import partie.Partie;
import partie.exceptions.ChoixDejaFaitException;
import partie.exceptions.PasAssezDeRessourcesException;
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

    private final VueMenuNonConnecte vueMenuNonConnecte;
    private final VueConnexion vueConnexion;
    private final VueInscription vueInscription;
    private final VueMenuConnecte vueMenuConnecte;
    private final VuePartie vuePartie;
    private final VueWaitingRoom vueWaitingRoom;
    private final int NB_JOUEURS = 4;

    private IJoueur joueur;
    private String nom;
    private User user;
    private IPartie partie;
    private static int nbUserWaitingRoom;


    public Controleur(Stage stage) throws IOException, NotBoundException
    {
        facade = new ProxySevenWondersOnLineImpl();
        vueMenuNonConnecte = VueMenuNonConnecte.creerVue(stage);
        vueMenuNonConnecte.initialiserControleur(this);
        vueConnexion = VueConnexion.creerVue(stage);
        vueConnexion.initialiserControleur(this);
        vueInscription = VueInscription.creerVue(stage);
        vueInscription.initialiserControleur(this);
        vueMenuConnecte = VueMenuConnecte.creer(stage, this);
        vueMenuConnecte.initialiserControleur(this);
        vuePartie = VuePartie.creerVue(stage,this);
        vueWaitingRoom = VueWaitingRoom.creerVue(stage);
        vueWaitingRoom.initialiserControleur(this);

    }

    public void run() throws RemoteException {
        vueMenuNonConnecte.show();
    }

    public void miseEnPlaceDuJeu() throws RemoteException
    {

        List<Partie> lj = this.facade.getParties();
        System.out.println(lj);
        for(Partie p : lj){
            for(IJoueur j : p.getListeDesJoueurs()){
                if (j.getNom().equals(this.user.getPseudo())){
                    this.partie = p;
                    System.out.println("Partie ou le controleur est" + partie);
                }
            }
        }
        List<IJoueur> lt = this.partie.getListeDesJoueurs();
        System.out.println(lt);
        for (IJoueur joueur: lt) {
            if (user.getPseudo().equals(joueur.getNom()))
            {
                this.joueur = joueur;
            }
        }
        if (joueur.getNom().equals("jlietard")) {
            facade.miseEnPlaceDuJeu(joueur);
        }

        System.out.println(joueur.getMerveille());
        System.out.println(partie.getListeDesJoueurs());
    }


    public void passerALaSuite()
    {
        partie.suitePartie();
    }

    public IPartie getPartie() {
        return partie;
    }

    public void setPartie(IPartie partie) {
        this.partie = partie;
    }

    public ProxySevenWondersOnLine getFacade()
    {
        return facade;
    }


    public void goToPartie() throws RemoteException
    {
        vuePartie.chargerDonnees();
        this.vuePartie.show();
    }

    public Boolean connexion(String pseudo,String mdp) throws PseudoOuMotDePasseIncorrectException, RemoteException, NotBoundException, MalformedURLException
    {
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

    public Boolean inscription(String pseudo,String mdp) throws PseudoDejaPrisException, RemoteException, NotBoundException, MalformedURLException
    {
        boolean inscriptionReussie = false;
        this.user = this.facade.inscriptionUser(pseudo,mdp);
        if(!Objects.isNull(this.user)){
            inscriptionReussie = true;
        }
        return inscriptionReussie;
    }

    public List<User> getAmis()
    {
        return this.user.getAmis();
    }
    public void exit()
    {
        System.exit(0);
    }

    public void addFriend(User user, String pseudoAmi)
    {
        try{
            this.facade.ajouterJoueurEnAmi( pseudoAmi);
        } catch (RemoteException | JoueurDejaDansLaListeDAmisException | JoueurNonExistantException e) {
            e.printStackTrace();
        }
    }

    public void inviterUser(int idPartie, User user) throws JoueurDejaAjouteException, MaxJoueursAtteintException, JoueurNonExistantException
    {
        try {
            this.facade.inviterUser(idPartie, user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void construireEtape() throws ChoixDejaFaitException, PasAssezDeRessourcesException, RemoteException, MaximumEtapeAtteintException
    {
        ICarte carte = vuePartie.getCarte();
        partie.construireEtape(joueur,carte);
        vuePartie.affichageInteractifDesVariables();
        System.out.println("carte construite");
        vuePartie.attendreChoixAdversaires(this.joueur.getAJoue());
    }

    public void defausserCarte() throws ChoixDejaFaitException, RemoteException
    {
        ICarte carte = vuePartie.getCarte();
        partie.deffausserCarte(joueur, carte);
        vuePartie.affichageInteractifDesVariables();
        System.out.println("carte defausser");
        vuePartie.attendreChoixAdversaires(this.joueur.getAJoue());
    }

    public void jouerCarte() throws ChoixDejaFaitException, RemoteException, PasAssezDeRessourcesException
    {
        ICarte carte = vuePartie.getCarte();
        partie.jouerCarte(joueur, carte);
        vuePartie.affichageInteractifDesVariables();
        System.out.println("carte joue");
        vuePartie.attendreChoixAdversaires(this.joueur.getAJoue());
    }

    public User getUser()
    {
        return this.user;
    }

    public IJoueur getJoueurDroite()
    {
        int indice = partie.getListeDesJoueurs().indexOf(this.joueur);
        return partie.getListeDesJoueurs().get(voisinDeDroite(indice));
    }

    public IJoueur getJoueurGauche()
    {
        int indice = partie.getListeDesJoueurs().indexOf(this.joueur);
        return partie.getListeDesJoueurs().get(voisinDeGauche(indice));
    }
    public IJoueur getJoueurFace()
    {
        int indice = partie.getListeDesJoueurs().indexOf(this.joueur);
        return partie.getListeDesJoueurs().get(joueurFace(indice));
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
    public IJoueur getJoueur()
    {
        return joueur;
    }

    public void goToMenu() {
        this.vueMenuNonConnecte.show();
    }

    public void goToWaitingRoom()
    {
        vueWaitingRoom.chargerDonnees();
        vueWaitingRoom.show();
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

    public String getNom() {
        return this.nom;
    }

    public int getNbUserWaitingRoom() throws RemoteException {
        return this.facade.getUserDansPreLobby().size();
    }

    public void ajoutUserWaitingRoom(User u , Partie p) throws RemoteException {

            Map<User, Partie> map = this.facade.getUserDansPreLobby();
        if (map.keySet().stream()
                .noneMatch(user1 ->
                        user1.getPseudo()
                                .equals(u.getPseudo()))) {
                map.put(u, p);
                this.facade.setUserDansPreLobby(map);

        }
    }

}
