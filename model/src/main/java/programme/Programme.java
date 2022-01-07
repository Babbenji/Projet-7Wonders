package programme;


import com.fasterxml.jackson.databind.ObjectMapper;
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
    }
}
