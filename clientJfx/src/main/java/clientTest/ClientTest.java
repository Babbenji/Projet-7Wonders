package clientTest;

import modele.ProxySevenWondersOnLine;
import modele.ProxySevenWondersOnLineImpl;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientTest {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, PseudoOuMotDePasseIncorrectException {
        ProxySevenWondersOnLine proxySevenWondersOnLine = new ProxySevenWondersOnLineImpl();
        User u = proxySevenWondersOnLine.connexionUser("jlietard","password");
        System.out.println(u);
        for(User user : u.getAmis()){
            System.out.println(user.getPseudo());
        }
    }
}
