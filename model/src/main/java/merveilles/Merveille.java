package merveilles;

import type.IMerveille;

import java.io.Serializable;
import java.util.HashMap;

public class Merveille implements IMerveille, Serializable {


    private String ressourceProduite; // ce que produit la merveille de base
    private String cite; // le nom de la merveille
    private int etape; // l'étape actuelle de cette étape
    private String image; // le nom de l'image (pour JFX)

    //Pour chaque étape, son effet et ses ressources requises
    //A savoir qu'on travaille sur la face A des merveilles donc il n'y a que l'étape 2 qui donne autre chose que des pts de victoire
    //Premiere etape
    private HashMap<String,Integer> ressourcesPremiereEtape;
    private HashMap<String,String> effetPremiereEtape;

    //Deuxieme etape
    private HashMap<String,Integer> ressourcesDeuxiemeEtape;
    private HashMap<String,String> effetDeuxiemeEtape;

    //Troisieme etape
    private HashMap<String,Integer> ressourcesTroisiemeEtape;
    private HashMap<String,String> effetTroisiemeEtape;

    private HashMap<String,Integer> ressourceEtapeCourante; //ressources nécessaires pour construire l'étape à suivre

    public Merveille() {
        super();
    }

    //Constructeur
    public Merveille(String ressourceProduite, String cite, HashMap<String, Integer> ressourcesPremiereEtape, HashMap<String, String> effetPremiereEtape, HashMap<String, Integer> ressourcesDeuxiemeEtape, HashMap<String, String> effetDeuxiemeEtape, HashMap<String, Integer> ressourcesTroisiemeEtape, HashMap<String, String> effetTroisiemeEtape) {
        this.ressourceProduite = ressourceProduite;
        this.cite = cite;
        this.etape = 0; //on commence sans étape déjà construite
        this.ressourcesPremiereEtape = ressourcesPremiereEtape;
        this.effetPremiereEtape = effetPremiereEtape;
        this.ressourcesDeuxiemeEtape = ressourcesDeuxiemeEtape;
        this.effetDeuxiemeEtape = effetDeuxiemeEtape;
        this.ressourcesTroisiemeEtape = ressourcesTroisiemeEtape;
        this.effetTroisiemeEtape = effetTroisiemeEtape;
    }

     //fonctions autres
    //getters et setters générés, à voir ensuite les fonctions inutiles à retirer
    //Je me passe de commentaires pour les getters et setters générés, ils n'ont rien de particulier

    @Override
    public String getRessourceProduite() {
        return ressourceProduite;
    }

    @Override
    public void setRessourceProduite(String ressourceProduite) {
        this.ressourceProduite = ressourceProduite;
    }

    @Override
    public String getCite() {
        return cite;
    }

    @Override
    public void setCite(String cite) {
        this.cite = cite;
    }

    @Override
    public int getEtape() {
        return etape;
    }

    @Override
    public void setEtape(int etape) {
        this.etape = etape;
    }

    @Override
    public HashMap<String, Integer> getRessourcesPremiereEtape() {
        return ressourcesPremiereEtape;
    }

    @Override
    public void setRessourcesPremiereEtape(HashMap<String, Integer> ressourcesPremiereEtape) {
        this.ressourcesPremiereEtape = ressourcesPremiereEtape;
    }

    @Override
    public HashMap<String, String> getEffetPremiereEtape() {
        return effetPremiereEtape;
    }

    @Override
    public void setEffetPremiereEtape(HashMap<String, String> effetPremiereEtape) {
        this.effetPremiereEtape = effetPremiereEtape;
    }

    @Override
    public HashMap<String, Integer> getRessourcesDeuxiemeEtape() {
        return ressourcesDeuxiemeEtape;
    }

    @Override
    public void setRessourcesDeuxiemeEtape(HashMap<String, Integer> ressourcesDeuxiemeEtape) {
        this.ressourcesDeuxiemeEtape = ressourcesDeuxiemeEtape;
    }

    @Override
    public HashMap<String, String> getEffetDeuxiemeEtape() {
        return effetDeuxiemeEtape;
    }

    @Override
    public void setEffetDeuxiemeEtape(HashMap<String, String> effetDeuxiemeEtape) {
        this.effetDeuxiemeEtape = effetDeuxiemeEtape;
    }

    @Override
    public HashMap<String, Integer> getRessourcesTroisiemeEtape() {
        return ressourcesTroisiemeEtape;
    }

    @Override
    public void setRessourcesTroisiemeEtape(HashMap<String, Integer> ressourcesTroisiemeEtape) {
        this.ressourcesTroisiemeEtape = ressourcesTroisiemeEtape;
    }

    @Override
    public HashMap<String, String> getEffetTroisiemeEtape() {
        return effetTroisiemeEtape;
    }

    @Override
    public void setEffetTroisiemeEtape(HashMap<String, String> effetTroisiemeEtape) {
        this.effetTroisiemeEtape = effetTroisiemeEtape;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public HashMap<String, Integer> getRessourceEtapeCourante() {
        return ressourceEtapeCourante;
    }

    @Override
    public void setRessourceEtapeCourante(HashMap<String, Integer> ressourceEtapeCourante) {
        this.ressourceEtapeCourante = ressourceEtapeCourante;
    }

    //fonctions spécifiques (pas getters et setters)

    //retourne la prix en ressources de la prochaine étape
    @Override
    public HashMap<String, Integer> prixEtapeSuivante() {
        switch(etape){
            case 0: //on retourne le coût de l'étape qui suit celle actuelle
                return ressourcesPremiereEtape;
            case 1:
                return ressourcesDeuxiemeEtape;
            case 2:
                return ressourcesTroisiemeEtape;
        }
        return null;
    }

    @Override
    public boolean peutAcheterEtape() {
        return false;
    }

    @Override
    public String toString() {
        return "Merveille{" +
                "ressourceProduite='" + ressourceProduite + '\'' +
                ", cite='" + cite + '\'' +
                ", etape=" + etape +
                '}';
    }
}
