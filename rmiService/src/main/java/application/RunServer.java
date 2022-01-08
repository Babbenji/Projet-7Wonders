package application;

import service.ServiceSevenWondersOnline;
import service.ServiceSevenWondersOnlineImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RunServer {

    public static final String RMI_SERVEUR = "rmi://localhost/seven-wonders";

    public static void main(String[] args) {
        try {
            // Définition du port pour la communication RMI
            LocateRegistry.createRegistry(1099);


            // Création de l'instance du service qui va être embarqué dans le serveur RMI
            ServiceSevenWondersOnline serviceSevenWondersOnLine = ServiceSevenWondersOnlineImpl.creer();

            // Association de l'adresse à au service
            Naming.rebind(RMI_SERVEUR, (Remote) serviceSevenWondersOnLine);

            System.out.println("Serveur lancé");
        } catch ( MalformedURLException | RemoteException e)
        {
            e.printStackTrace();
        }
    }
}
