package services.exceptions;

public class PseudoOuMotDePasseIncorrectException extends Exception {

    @Override
    public String toString() {
        return "Erreur, le pseudo et le mot de passe ne correspondent pas.";
    }
}
