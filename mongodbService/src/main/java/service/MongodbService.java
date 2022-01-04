package mongodbService.src.main.java.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongodbService {

    private static final String connectionString = "mongodb+srv://root:root@cluster0.o30qp.mongodb.net/sevenwonders?retryWrites=true&w=majority";
    private static final String dbName = "sevenwonders";

    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongodbService() throws ConnectBDExeption{
        try {
            this.mongoClient = MongoClients.create(connectionString);
        } catch (Exception e) {
            throw new ConnectBDExeption();
        }
        this.database = this.mongoClient.getDatabase(dbName);
    }
}
