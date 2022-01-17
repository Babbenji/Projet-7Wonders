package programmeTest;

import facade.FacadeSevenWondersOnLine;
import facade.FacadeSevenWondersOnlineImpl;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

import java.rmi.RemoteException;

public class TestFacade {

    public static void main(String[] args) throws PseudoOuMotDePasseIncorrectException {
        FacadeSevenWondersOnLine facadeSevenWondersOnLine = new FacadeSevenWondersOnlineImpl();

        User u = facadeSevenWondersOnLine.connexionUser("jlietard","password");
        System.out.println(u);

        for(User user : u.getAmis()){
            System.out.println(user.getPseudo());
        }
    }
}
