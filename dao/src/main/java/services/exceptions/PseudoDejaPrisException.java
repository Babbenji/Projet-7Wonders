package services.exceptions;

public class PseudoDejaPrisException extends Exception {
    @Override
    public String toString() {
        return "Erreur, Ce pseudo est déjà pris.";
    }
}
