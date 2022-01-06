package user;

import interfaces.exceptions.JoueurDejaDansLaListeDAmisException;
import interfaces.exceptions.JoueurNonExistantException;
import org.bson.codecs.pojo.annotations.BsonProperty;


import java.util.ArrayList;
import java.util.List;

public class User {

    @BsonProperty(value ="pseudo")
    private String pseudo;

    @BsonProperty(value ="password")
    private String password;

    @BsonProperty(value ="friends")
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
     * Permet de retourner le pseudo de l'utilisateur
     * @return pseudo
     */
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Permet de retourner le mot de passe de l'utilisateur
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Permet de modifier le mot de passe de l'utilisateur
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Permet de retourner la liste des amis
     * @return amis
     */
    public List<User> getAmis() {
        return this.amis;
    }

}
