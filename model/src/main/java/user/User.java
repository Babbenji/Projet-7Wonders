package user;

import type.IUser;
import org.bson.codecs.pojo.annotations.BsonProperty;


import java.util.ArrayList;
import java.util.List;

public class User implements IUser {

    @BsonProperty(value ="pseudo")
    private String pseudo;

    @BsonProperty(value ="password")
    private String password;

    @BsonProperty(value ="friends")
    private List<IUser> amis;

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
    @Override
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Permet de retourner le mot de passe de l'utilisateur
     * @return password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Permet de modifier le mot de passe de l'utilisateur
     * @param password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Permet d'ajouter/modifier le pseudo de l'user
     * @param pseudo
     */
    @Override
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Permet d'ajouter/modifier la liste d'amis
     * @param amis
     */
    @Override
    public void setAmis(List<IUser> amis) {
        this.amis = amis;
    }

    /**
     * Permet de retourner la liste des amis
     * @return amis
     */
    @Override
    public List<IUser> getAmis() {
        return this.amis;
    }

}
