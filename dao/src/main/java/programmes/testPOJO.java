package programmes;

import interfaces.type.IUser;
import services.exceptions.JoueurDejaDansLaListeDAmisException;
import services.exceptions.JoueurNonExistantException;
import services.MongodbService;
import services.exceptions.ConnectBDExeption;
import services.exceptions.PseudoDejaPrisException;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

import java.util.logging.Level;
import java.util.logging.Logger;

public class testPOJO {
    public static void main(String[] args) throws ConnectBDExeption, PseudoOuMotDePasseIncorrectException, JoueurDejaDansLaListeDAmisException, JoueurNonExistantException, PseudoDejaPrisException {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE); // e.g. or Log.WARNING, etc.
        //Connection a la bdd
        MongodbService mongodbService = new MongodbService();

        System.out.println("----------------------------------");
        System.out.println("Début du programme de test :");
        System.out.println("----------------------------------");
        System.out.println();

        /*

        System.out.println("----------------------------------");
        System.out.println("Inscription de Julien, Timothé, Matthieu et Aziz--------------------");
        mongodbService.createUser("jlietard", "password");
        mongodbService.createUser("tdurand", "password");
        mongodbService.createUser("mblond", "password");
        mongodbService.createUser("abenjazia", "password");

         */
        //mongodbService.createUser("jlietard", "password");
        System.out.println("Tous les utilisateurs--------------------");

        for(User user : mongodbService.getAllUsers()) {
            System.out.println("- "+user.getPseudo());
        }

        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();

        System.out.println("L'utilisateur dont le pseudo est \"jlietard\"--------------------");
        System.out.println(mongodbService.getUserByPseudo("jlietard").toString());

        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();

        System.out.println("La liste d'amis de mblond :");
        for (IUser ami: mongodbService.getUserByPseudo("mblond").getAmis()) {
            System.out.println("- "+ami.getPseudo());
        }
        /*
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();
        System.out.println("Ajout d'un ami dans la liste d'amis de Julien--------------------");
        mongodbService.addFriendUser("jlietard", "abenjazia");
         */

        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();
        System.out.println("La liste d'amis de jlietard :");
        for (IUser ami: mongodbService.getUserByPseudo("jlietard").getAmis()) {
            System.out.println("- "+ami.getPseudo());
        }


        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();
        System.out.println("Connexion de jlietard--------------------");
        if(mongodbService.loginUser("jlietard", "password")!=null) {
            System.out.println("...........................");
            System.out.println("... Connexion réussie ! ...");
            System.out.println("...........................");
        }

    }
}
