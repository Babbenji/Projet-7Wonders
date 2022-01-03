package user;

import interfaces.exceptions.JoueurDejaDansLaListeDAmisException;
import interfaces.exceptions.JoueurNonExistantException;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String pseudo;
    private List<User> amis;

    /**
     * Permet d'ajouter un utilisateur à sa liste d'amis
     * @param pseudo
     */
    public User(String pseudo) {
        this.pseudo = pseudo;
        this.amis = new ArrayList<>();
    }

    /**
     * Permet d'ajouter un utilisateur à sa liste d'amis
     * @param pseudo
     * @throws JoueurNonExistantException : Le joueur n'existe pas
     * @throws JoueurDejaDansLaListeDAmisException : Le joueur est déjà dans la liste d'amis
     */
    public void addFriend(String pseudo) throws JoueurNonExistantException, JoueurDejaDansLaListeDAmisException {
        for (User ami : this.amis) {
            if(ami.getPseudo().equals(pseudo)){
                throw new JoueurDejaDansLaListeDAmisException();
            }
            else {
                this.amis.add(this.getUserByPseudo(pseudo)); //rajouter la condition du si le joueur existe quand la bdd sera faite
            }
        }
    }

    /**
     * Permet de retrouver un joueur via son pseudo
     * @param pseudo
     * @return
     */
    public User getUserByPseudo(String pseudo){ //Cette méthode ne sera pas dans la classe User !!!!!!
        return this;//requete mongodb
    }

    /**
     * Permet de retourner le pseudo de l'utilisateur
     * @return pseudo
     */
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Permet de retourner la liste des amis
     * @return amis
     */
    public List<User> getAmis() {
        return this.amis;
    }
}
