package serviceTest;

import service.ServiceSevenWondersOnline;
import service.ServiceSevenWondersOnlineImpl;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

import java.rmi.RemoteException;

public class ServiceTest {
    public static void main(String[] args) throws RemoteException, PseudoOuMotDePasseIncorrectException {
        ServiceSevenWondersOnline serviceSevenWondersOnline = new ServiceSevenWondersOnlineImpl();
        User u = serviceSevenWondersOnline.connexionUser("jlietard", "password");
        System.out.println(u);
        for(User user : u.getAmis()){
            System.out.println(user.getPseudo());
        }

        System.out.println(serviceSevenWondersOnline.getUserByPseudo("tdurand"));

    }
}
