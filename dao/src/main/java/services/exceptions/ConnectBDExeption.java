package services.exceptions;

public class ConnectBDExeption extends Exception {
    @Override
    public String toString() {
        return "Erreur lors de la connexion à la base de données.";
    }
}
