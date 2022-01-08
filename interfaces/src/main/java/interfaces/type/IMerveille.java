package interfaces.type;

import java.util.HashMap;

public interface IMerveille {
    String getRessourceProduite();

    void setRessourceProduite(String ressourceProduite);

    String getCite();

    void setCite(String cite);

    int getEtape();

    void setEtape(int etape);

    HashMap<String, Integer> getRessourcesPremiereEtape();

    void setRessourcesPremiereEtape(HashMap<String, Integer> ressourcesPremiereEtape);

    HashMap<String, String> getEffetPremiereEtape();

    void setEffetPremiereEtape(HashMap<String, String> effetPremiereEtape);

    HashMap<String, Integer> getRessourcesDeuxiemeEtape();

    void setRessourcesDeuxiemeEtape(HashMap<String, Integer> ressourcesDeuxiemeEtape);

    HashMap<String, String> getEffetDeuxiemeEtape();

    void setEffetDeuxiemeEtape(HashMap<String, String> effetDeuxiemeEtape);

    HashMap<String, Integer> getRessourcesTroisiemeEtape();

    void setRessourcesTroisiemeEtape(HashMap<String, Integer> ressourcesTroisiemeEtape);

    HashMap<String, String> getEffetTroisiemeEtape();

    void setEffetTroisiemeEtape(HashMap<String, String> effetTroisiemeEtape);

    String getImage();

    void setImage(String image);

    HashMap<String, Integer> getRessourceEtapeCourante();

    void setRessourceEtapeCourante(HashMap<String, Integer> ressourceEtapeCourante);

    //retourne la prix en ressources de la prochaine étape
    HashMap<String, Integer> prixEtapeSuivante();

    //TO DO: finir cette méthode permettant de vérifier (avec la liste des ressources d'un joueur (liste ou joueur en parametre ?), si il peut construire l'étape suivante)
    boolean peutAcheterEtape();
}
