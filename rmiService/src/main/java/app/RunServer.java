package app;

import service.ServiceSevenWondersOnline;
import service.ServiceSevenWondersOnlineImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RunServer {
    public static final String RMI_SERVER = "rmi://localhost/sevenWonders";


    public static void main(String[] args) {
        try {
            ServiceSevenWondersOnline serviceSevenWondersOnline = new ServiceSevenWondersOnlineImpl();

            Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            ServiceSevenWondersOnline facadeServeur = (ServiceSevenWondersOnline) UnicastRemoteObject.exportObject(serviceSevenWondersOnline,1099);
            Naming.rebind(RMI_SERVER,facadeServeur);
            System.out.println("Serveur lancé");
        } catch (RemoteException | MalformedURLException e)
        {
            e.printStackTrace();
        }
    }
}
