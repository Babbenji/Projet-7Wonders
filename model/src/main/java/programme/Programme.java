package programme;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import merveilles.Merveille;


import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Programme {
    public static void main(String[] args) {
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();


            // convert JSON array to list of books
            List<Merveille> merveilles = Arrays.asList(mapper.readValue(Paths.get("model/src/main/resources/json/merveilles.json").toFile(), Merveille[].class));

            // print books
            merveilles.forEach(c -> System.out.println(c.getCite()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ConnectionString connectionString = new ConnectionString("mongodb+srv://root:root@cluster0.o30qp.mongodb.net/sevenwonders?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");



    }
}
