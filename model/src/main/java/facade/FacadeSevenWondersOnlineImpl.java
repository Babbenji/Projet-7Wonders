package facade;

import cartes.Carte;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.exceptions.JoueurDejaDansLaListeDAmisException;
import interfaces.exceptions.JoueurNonExistantException;
import interfaces.exceptions.PartieNonTermineeException;
import interfaces.facade.FacadeSevenWondersOnLine;
import interfaces.type.ICarte;
import interfaces.type.IJoueur;
import interfaces.type.IMerveille;
import joueur.Joueur;
import merveilles.Merveille;
import partie.Partie;
import services.MongodbService;
import services.exceptions.PseudoDejaPrisException;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

import java.nio.file.Paths;
import java.util.*;

public class FacadeSevenWondersOnlineImpl implements FacadeSevenWondersOnLine {
    private Map<Integer,Partie> parties;
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
    public void inscriptionUser(String nom, String pw) throws PseudoOuMotDePasseIncorrectException, PseudoDejaPrisException {
        this.mongodbService.createUser(nom, pw);
        this.user = mongodbService.getUserByPseudo(nom);
        connexionUser(this.user.getPseudo(), this.user.getPassword());
    }

    @Override
    public void connexionUser(String pseudo, String pw) throws PseudoOuMotDePasseIncorrectException {
        this.mongodbService.loginUser(pseudo, pw);
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

}
