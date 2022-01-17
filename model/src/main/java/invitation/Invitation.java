package invitation;

import java.io.Serializable;

public class Invitation implements Serializable {



    private int idPartie; // Identifiant de la partie concernée par l'invitation
    private String joueurCreateur; // Créateur de la partie concernée par l'invitation

    public int getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    public String getJoueurCreateur() {
        return joueurCreateur;
    }

    public void setJoueurCreateur(String joueurCreateur) {
        this.joueurCreateur = joueurCreateur;
    }
}