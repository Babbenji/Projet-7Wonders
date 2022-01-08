package vues;

import controleur.Controleur;

public interface Vue
{
    void show();

    void chargerDonnees();

    void initialiserControleur(Controleur controleur);
}
