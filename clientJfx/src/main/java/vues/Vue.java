package vues;

import controleur.Controleur;

import java.rmi.RemoteException;

public interface Vue
{
    void show();

    void chargerDonnees() throws RemoteException;

    void initialiserControleur(Controleur controleur);
}
