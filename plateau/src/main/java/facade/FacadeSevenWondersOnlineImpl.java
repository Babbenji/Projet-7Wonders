package facade;

import cartes.Carte;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.PartieNonTermineeException;
import joueur.Joueur;
import merveilles.Merveille;
import partie.Partie;
import services.MongodbService;
import services.exceptions.JoueurDejaDansLaListeDAmisException;
import services.exceptions.JoueurNonExistantException;
import services.exceptions.PseudoDejaPrisException;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import type.ICarte;
import type.IJoueur;
import type.IMerveille;
import user.User;

import java.nio.file.Paths;
import java.util.*;

public class FacadeSevenWondersOnlineImpl implements FacadeSevenWondersOnLine {
    private Map<Integer, Partie> parties;
    private User user;
    private List<User> joueursInscrits;
    private Map<User,String> utilisateursConnectes;
    private Map<User,Partie> userDansPreLobby;
    private List<ICarte> lesCartes;
    private List<IMerveille> lesMerveilles;
    private Map<IJoueur,Partie> associationJoueurPartie;


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

    MongodbService mongodbService = new MongodbService();

    @Override
    public User inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException {
        this.mongodbService.createUser(nom, pw);
        this.user = mongodbService.getUserByPseudo(nom);
        return connexionUser(this.user.getPseudo(), this.user.getPassword());
    }

    @Override
    public User connexionUser(String pseudo, String pw) throws PseudoOuMotDePasseIncorrectException {
        System.out.println("Ici2");
        return this.mongodbService.loginUser(pseudo, pw);
    }

    @Override
    public void ajouterJoueurEnAmi(String pseudo) throws JoueurDejaDansLaListeDAmisException, JoueurNonExistantException {
        this.mongodbService.addFriendUser(user.getPseudo(), pseudo);
    }

    @Override
    public void getAmis(){
        this.user.getAmis();
    }

    @Override
    public void inviterJoueur(IJoueur joueur)
    {

    }

    @Override
    public void rejoindreUnePartie(String nom)
    {

    }

    @Override
    public void creePartie() {
        ArrayList<IJoueur> listJoueur = new ArrayList<>();
        for (User user: userDansPreLobby.keySet())
        {
            Joueur joueur1 = new Joueur(user.getPseudo());
            listJoueur.add(joueur1);
        }
        Partie partie = new Partie(listJoueur,lesCartes,lesMerveilles);
        parties.put(partie.getIdPartie(),partie);

        for (IJoueur joueur: listJoueur) {
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
    public void deffausserCarte(IJoueur joueur, ICarte carte) throws Exception {
        Partie partie = this.associationJoueurPartie.get(joueur);
        partie.deffausserCarte(joueur,carte);
    }

    @Override
    public void construireEtape(IJoueur joueur) throws Exception {
        Partie partie = this.associationJoueurPartie.get(joueur);
        partie.construireEtape(joueur);
    }


    @Override
    public String tableauScore(IJoueur joueur) throws PartieNonTermineeException
    {
        Partie partie = this.associationJoueurPartie.get(joueur);
        return partie.affichageDesScores();
    }

    @Override
    public void partieTerminee(IJoueur joueur) throws Exception
    {
        Partie partie = this.associationJoueurPartie.get(joueur);
        partie.partieTerminee();
    }

    public Map<Integer, Partie> getParties() {
        return parties;
    }

    public List<ICarte> getLesCartes() {
        return lesCartes;
    }

    public List<IMerveille> getLesMerveilles() {
        return lesMerveilles;
    }
}
