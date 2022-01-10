package app;

import service.ServiceSevenWondersOnline;
import service.ServiceSevenWondersOnlineImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RunServer {


    public static void main(String[] args) {
        try {
            ServiceSevenWondersOnline serviceSevenWondersOnline = new ServiceSevenWondersOnlineImpl();

            Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            ServiceSevenWondersOnline facadeServeur = (ServiceSevenWondersOnline) UnicastRemoteObject.exportObject(serviceSevenWondersOnline,1099);
            reg.bind("ServiceSevenWondersOnline", facadeServeur);
            System.out.println("Serveur lancé");
        } catch (RemoteException | AlreadyBoundException e)
        {
            e.printStackTrace();
        }
    }
}
