package serviceTest;

import service.ServiceSevenWondersOnline;
import service.ServiceSevenWondersOnlineImpl;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

import java.rmi.RemoteException;

public class ServiceTest {
    public static void main(String[] args) throws RemoteException, PseudoOuMotDePasseIncorrectException {
        ServiceSevenWondersOnline serviceSevenWondersOnline = new ServiceSevenWondersOnlineImpl();
        User u = serviceSevenWondersOnline.connexionUser("tdurand", "password");
        System.out.println(u);

    }
}
