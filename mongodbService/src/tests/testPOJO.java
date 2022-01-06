package mongodbService.src.tests;

import mongodbService.src.service.exceptions.ConnectBDExeption;
import mongodbService.src.service.MongodbService;

import user.User;

public class testPOJO {
    public static void main(String[] args) throws ConnectBDExeption {
        //Connection a la bdd
        MongodbService mongodbService = new MongodbService();

        System.out.println("Tous les utilisateurs--------------------");
        for(User user : mongodbService.getAllUsers()) {
            System.out.println(user.getPseudo());
        }
        System.out.println("----------------------------------");
    }
}
