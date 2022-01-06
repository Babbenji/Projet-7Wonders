package mongodbService.src.tests;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Updates.set;

public class testMONGO {
    public static void main(String[] args) {
        //Connection a la bdd

        String connectionString = "mongodb+srv://root:root@cluster0.o30qp.mongodb.net/sevenwonders?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(connectionString)){

            MongoCollection<Document> users = mongoClient.getDatabase("sevenwonders").getCollection("users");
            deleteDocument(users);
            createDocument(users);
            //updatesDocument(users);
            findDocument(users);
        }

    }

    private static void findDocument(MongoCollection<Document> collection) {
        List<Document> allUsers = collection.find().into(new ArrayList<>());
        printCollection(allUsers);
        List<Document> userWithId1 = collection.find(Filters.eq("user_id", 1)).into(new ArrayList<>());
        //printCollection(userWithId1);
        List<Document> userWithAge48 = collection.find(Filters.gte("age", 48)).into(new ArrayList<>());
        //printCollection(userWithAge48);
        List<Document>amis = collection.find(Filters.eq("pseudo", "mblond")).projection(exclude("friends")).into(new ArrayList<>());
        printCollection(amis);

    }

    private static void updatesDocument(MongoCollection<Document> collection) {
        Random random = new Random();

        List<Document> collectionItemList = collection.find().into(new ArrayList<>());
        collectionItemList.forEach(item -> {
            Object id = item.get("_id");
            FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
            Document filter = new Document("_id", id);
            Bson update = set("age", random.nextInt(100));
            item = collection.findOneAndUpdate(filter, update, findOneAndUpdateOptions);
            //System.out.println(item.toJson());
        });
    }

    private static void deleteDocument(MongoCollection<Document> collection) {
        collection.deleteMany(new Document());
    }

    private static void createDocument(MongoCollection<Document> collection) {
        List<Document> usersList = new ArrayList<>();
        //Map<String,String> user1 = Map.of("id","jlietard","password", "jlietard");
        //Map<String,String> user2 = Map.of("id","tdurand","password", "tdurand");
        //Map<String,String> user3 = Map.of("id","abenjazia","password", "abenjazia");
        //Map<String,String> user4 = Map.of("id","mblond","password", "mblond");
        Document user1 = new Document("user_id", 1).append("pseudo", "Julien");
        Document user2 = new Document("user_id", 2).append("pseudo", "Timothé");
        Document user3 = new Document("user_id", 3).append("pseudo", "Aziz");
        List<Document>nouveauxAmis = new ArrayList<>();
        nouveauxAmis.add(user1);
        nouveauxAmis.add(user2);
        Document user4 = new Document("user_id", 4).append("pseudo", "Matthieu").append("friends", nouveauxAmis);
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
        usersList.add(user4);
        //usersList.add(new Document("user_id", 1).append("pseudo", "Julien").append("friends", user1));
        //usersList.add(new Document("user_id", 2).append("pseudo", "Timothé").append("friends", user2));
        //usersList.add(new Document("user_id", 3).append("pseudo", "Aziz").append("friends", user3));
        //usersList.add(new Document("user_id", 4).append("pseudo", "Matthieu").append("friends", user4));
        collection.insertMany(usersList);
    }

    private static void printCollection(List<Document> collection) {
        collection.forEach(document -> System.out.println(document.toJson()));
    }
}
