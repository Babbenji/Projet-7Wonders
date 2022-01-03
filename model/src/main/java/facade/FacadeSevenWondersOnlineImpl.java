package facade;

import cartes.Carte;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.exceptions.PartieNonTermineeException;
import interfaces.facade.FacadeSevenWondersOnLine;
import joueur.Joueur;
import merveilles.Merveille;
import partie.Partie;
import user.User;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacadeSevenWondersOnlineImpl implements FacadeSevenWondersOnLine

{
    private Map<Integer,Partie> parties;
    private User user;
    private List<User> joueursInscrits;
    private Map<User,String> utilisateursConnectes;
    private Map<User,Partie> userDansPreLobby;
    private List<Carte> lesCartes;
    private List<Merveille> lesMerveilles;

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
    public void inviterJoueur(Joueur joueur) {

    }

    @Override
    public String creePartie(String joueur) {

        //Partie p = new Partie(this.userDansPreLobby,this.lesCartes,this.lesMerveilles);
        //p.getListeDesJoueurs().add(this.userDansPreLobby);`

        return "YUDAZUEUFIAZF";
    }

    @Override
    public void rejoindreUnePartie(String nom)  {

    }

    @Override
    public void miseEnPlaceDuJeu() {

    }

    @Override
    public void constructionDesListes() {

    }

    @Override
    public void joueursPret() {

    }

    @Override
    public void finAge() {

    }

    @Override
    public void choixCarteAJouee(String joueur, String choix)  {

    }

    @Override
    public void jouerCarteCommerce(int positionJoueur, String choix) {

    }

    @Override
    public void defausserCarte(int postionCarte, String choix) {

    }

    @Override
    public void debloquerUneEtapeMerveille(int positionJoueur, String choix) {

    }

    @Override
    public void passerAgeSuivant() {

    }

    @Override
    public void passerTourSuivant() {

    }

    @Override
    public Map<String, Integer> listesDesRessourcesDesVoisinsDeChaqueJoueur(int positionJoueur) {
        return null;
    }

    @Override
    public void batailleMilitaire() {

    }

    @Override
    public void nombreDePointsMilitaireAGagnerSelonAge() {

    }

    @Override
    public void partieTerminee() {

    }

    @Override
    public String getVainqueur(String joueur) throws PartieNonTermineeException {
        return null;
    }

    @Override
    public void finDePartie() {

    }


}
