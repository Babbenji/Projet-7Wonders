package facade;

import cartes.Carte;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.exceptions.PartieNonTermineeException;
import interfaces.type.ICarte;
import interfaces.type.IJoueur;
import joueur.Joueur;
import merveilles.Merveille;
import partie.Partie;
import user.User;

import java.nio.file.Paths;
import java.util.*;

public class FacadeSevenWondersOnlineImpl {
    private Map<Integer,Partie> parties;
    private User user;
    private List<User> joueursInscrits;
    private Map<User,String> utilisateursConnectes;
    private Map<User,Partie> userDansPreLobby;
    private List<Carte> lesCartes;
    private List<Merveille> lesMerveilles;
    private Map<Joueur,Partie> associationJoueurPartie;


    public FacadeSevenWondersOnlineImpl() {
        //mongodb this.user = null;
        this.userDansPreLobby = new HashMap<>();
        this.parties = new HashMap<>();
        //recuperer joueurs inscrits mongodb jsp si on peut ^^ this.joueursInscrits
        this.utilisateursConnectes = new HashMap<>();
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON array to list of books
            this.lesCartes = Arrays.asList(mapper.readValue(Paths.get("model/src/main/resources/json/cartes.json").toFile(), Carte[].class));
            this.lesMerveilles = Arrays.asList(mapper.readValue(Paths.get("model/src/main/resources/json/merveilles.json").toFile(), Merveille[].class));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void inscriptionUser(String nom) { //Exceptions à ajouter (identifiant déjà utilisé, les mdp correspondent pas, ...)
        if (true){ //Si l'inscription se passe parfaitement
            this.user = new User(nom);
            this.connexionUser(this.user.getPseudo()); //L'utilisateur se connecte
        } else {
            //Répète l'inscription avec les exceptions + msg d'erreur (la méthode à refaire avec boucle while)
        }
    }

    @Override
    public void connexionUser(String pseudo) {
        //test si dans db
        //sinon recuperation user existant getUserByPseudo
    }

    @Override
    public void ajouterJoueurEnAmi(String pseudo) {

    }

    @Override
    public void inviterJoueur(IJoueur joueur) {

    }

    @Override
    public void rejoindreUnePartie(String nom){

    }

    @Override
    public void creePartie() {
        ArrayList<Joueur> listJoueur = new ArrayList<>();
        for (User user: userDansPreLobby.keySet())
        {
            Joueur joueur1 = new Joueur(user.getPseudo());
            listJoueur.add(joueur1);
        }
        Partie partie = new Partie(listJoueur,lesCartes,lesMerveilles);
        parties.put(partie.getIdPartie(),partie);

        for (Joueur joueur: listJoueur) {
            this.associationJoueurPartie.put(joueur,partie);
        }
    }

    @Override
    public void miseEnPlaceDuJeu(IJoueur joueur)
    {
        Partie partie = this.associationJoueurPartie.get(joueur);
        partie.miseEnPlacePartie();
    }

    @Override
    public void jouerCarte(IJoueur joueur, ICarte carte) throws Exception {
        Partie partie = this.associationJoueurPartie.get(joueur);
        partie.jouerCarte(joueur,carte);

    }

    @Override
    public void deffausserCarte(Joueur joueur, ICarte carte) throws Exception {
        Partie partie = this.associationJoueurPartie.get(joueur);
        partie.deffausserCarte(joueur,carte);
    }

    @Override
    public void construireEtape(Joueur joueur) throws Exception {
        Partie partie = this.associationJoueurPartie.get(joueur);
        partie.construireEtape(joueur);
    }


    @Override
    public String tableauScore(Joueur joueur) throws PartieNonTermineeException
    {
        Partie partie = this.associationJoueurPartie.get(joueur);
        return partie.affichageDesScores();
    }

    @Override
    public void partieTerminee(Joueur joueur) throws Exception
    {
        Partie partie = this.associationJoueurPartie.get(joueur);
        partie.partieTerminee();
    }

}
