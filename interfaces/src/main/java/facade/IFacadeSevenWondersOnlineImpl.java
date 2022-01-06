package facade;

import interfaces.exceptions.PartieNonTermineeException;
import interfaces.facade.FacadeSevenWondersOnLine;
import interfaces.type.ICarte;
import interfaces.type.IJoueur;

public interface IFacadeSevenWondersOnlineImpl extends FacadeSevenWondersOnLine {
    @Override
    void inscriptionUser(String nom);

    @Override
    void connexionUser(String pseudo);

    @Override
    void ajouterJoueurEnAmi(String pseudo);

    @Override
    void inviterJoueur(IJoueur joueur);

    @Override
    void rejoindreUnePartie(String nom);

    @Override
    void creePartie();

    @Override
    void miseEnPlaceDuJeu(IJoueur joueur);

    @Override
    void jouerCarte(IJoueur joueur, ICarte carte) throws Exception;

    @Override
    void deffausserCarte(IJoueur joueur, ICarte carte) throws Exception;

    @Override
    void construireEtape(IJoueur joueur) throws Exception;

    @Override
    String tableauScore(IJoueur joueur) throws PartieNonTermineeException;

    @Override
    void partieTerminee(IJoueur joueur) throws Exception;
}
