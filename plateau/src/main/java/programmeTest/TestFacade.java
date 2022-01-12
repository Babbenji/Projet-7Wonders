package programmeTest;

import facade.FacadeSevenWondersOnLine;
import facade.FacadeSevenWondersOnlineImpl;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

public class TestFacade {

    public static void main(String[] args) throws PseudoOuMotDePasseIncorrectException {
        FacadeSevenWondersOnLine facadeSevenWondersOnLine = new FacadeSevenWondersOnlineImpl();

        User u = facadeSevenWondersOnLine.connexionUser("tdurand","password");
        System.out.println(u);
    }
}
