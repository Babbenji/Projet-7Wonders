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

    @Override
    public String toString() {
        return "User{" +
                "pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", amis=" + amis +
                '}';
    }

    /**
     * Permet de créer un user
     */
    public User() {
        this.amis = new ArrayList<>();
    }

    /**
     * Permet de créer un user
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
     * Permet d'ajouter/modifier le pseudo de l'user
     * @param pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Permet d'ajouter/modifier la liste d'amis
     * @param amis
     */
    public void setAmis(List<User> amis) {
        this.amis = amis;
    }

    /**
     * Permet de retourner la liste des amis
     * @return amis
     */
    public List<User> getAmis() {
        return this.amis;
    }

}
