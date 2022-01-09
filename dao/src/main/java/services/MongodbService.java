package services;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import services.exceptions.JoueurDejaDansLaListeDAmisException;
import services.exceptions.JoueurNonExistantException;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import services.exceptions.PseudoDejaPrisException;
import services.exceptions.PseudoOuMotDePasseIncorrectException;
import user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.include;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongodbService {

    private final MongoDatabase mongoDatabase;
    public static final String NOM_BD = "sevenwonders";

    // connection au serveur mongodb
    MongoClient mongoClient = MongoClients.create(
            new ConnectionString("mongodb+srv://root:root@cluster0.o30qp.mongodb.net/sevenwonders?retryWrites=true&w=majority"));

    /**
     * Construit les services de la database sevenwonders avec POJO
     */
    public MongodbService() {
        CodecRegistry pojoCodecRegistry =
                fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder()
                                .automatic(true).build()
                        )
                );
        // Enable MongoDB logging in general
        System.setProperty("DEBUG.MONGO", "false");

        // Enable DB operation tracing
        System.setProperty("DB.TRACE", "false");
        // accès à la database sevenwonders
        this.mongoDatabase = this.mongoClient.getDatabase(NOM_BD).withCodecRegistry(pojoCodecRegistry);
    }

    /**
     * Retourner un objet User par rapport à un pseudo en paramètre
     * @return user
     */
    public User getUserByPseudo(String pseudo){
        MongoCollection<User> users = this.mongoDatabase.getCollection("users",User.class);
        return users.find(Filters.eq("pseudo", pseudo)).first();
    }

    /**
     * Insérer un nouveau user dans la base de données
     * @param pseudo pseudo du nouvel user
     * @param pw mot de passe du nouvel user
     * @throws PseudoDejaPrisException
     */
    public void createUser(String pseudo, String pw) throws PseudoDejaPrisException {
        boolean pseudoDejaPrisBoolean = false;
        MongoCollection<Document> users = this.mongoDatabase.getCollection("users");
        if (verifUserByPseudo(pseudo)){
            pseudoDejaPrisBoolean = true;
        }
        if (pseudoDejaPrisBoolean){
            throw new PseudoDejaPrisException();
        } else {
            List<Document> nouveauxAmis = new ArrayList<>();
            Document user = new Document().append("pseudo", pseudo).append("password", pw).append("friends", nouveauxAmis);
            users.insertOne(user);
        }
    }

    /**
     * Retourner tous les users
     * @return allUsers
     */
    public Collection<User> getAllUsers(){
        MongoCollection<User> users = this.mongoDatabase.getCollection("users",User.class);
        Collection<User> allUsers = new ArrayList<>();
        users.find().forEach((Consumer<? super User>) allUsers::add);
        return allUsers;
    }

    /**
     * Retourner une liste de joueurs dans la liste d'ami du joueur dont le pseudo est en paramètre
     * @param pseudo
     * @return friends
     */
    public List<User> getFriendsUser(String pseudo){
        MongoCollection<User> users = this.mongoDatabase.getCollection("users",User.class);
        List<User>friends = new ArrayList<>();
        users.find(Filters.eq("pseudo", pseudo)).projection(include("friends")).forEach((Consumer<? super User>) friends::add);
        return friends;
    }

    /**
     * Permet d'ajouter un utilisateur à la liste d'amis d'un User
     * @param pseudo pseudo de l'user qui ajoute un ami
     * @param nouvelAmi pseudo de l'ami qu'on veut ajouter
     * @throws JoueurNonExistantException : Le joueur n'existe pas
     * @throws JoueurDejaDansLaListeDAmisException : Le joueur est déjà dans la liste d'amis
     */
    public void addFriendUser(String pseudo, String nouvelAmi) throws JoueurNonExistantException, JoueurDejaDansLaListeDAmisException {
        MongoCollection<User> users = this.mongoDatabase.getCollection("users",User.class);
        User user = users.find(Filters.eq("pseudo", pseudo)).first();
        boolean estDejaAmi = false;
        int i = 0;
        if (!user.getAmis().isEmpty()) {
            while (!estDejaAmi && i <= user.getAmis().size()) {
                if (user.getAmis().iterator().next().getPseudo().equals(nouvelAmi)) {
                    estDejaAmi = true;
                }
                i++;
            }
        }

        if (verifUserByPseudo(nouvelAmi)) {
            if (!estDejaAmi) {
                MongoCollection<Document> usersInDatabase = mongoClient.getDatabase("sevenwonders").getCollection("users");

                FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
                Document newUser = usersInDatabase.find(Filters.eq("pseudo", nouvelAmi)).first();
                List<Document> amis = usersInDatabase.find(Filters.eq("pseudo", pseudo)).projection(include("friends")).projection(exclude("_id", "pseudo", "password")).into(new ArrayList<>());
                amis.add(newUser);
                Bson filter = Filters.eq("pseudo", pseudo);
                Bson update = Updates.push("friends", newUser);
                usersInDatabase.findOneAndUpdate(filter, update, findOneAndUpdateOptions);
            } else {
                throw new JoueurDejaDansLaListeDAmisException();
            }
        }
        else {
            throw new JoueurNonExistantException();
        }
    }

    /**
     * Retourner le user si les identifiants correspondent, sinon retourner null
     * @throws PseudoOuMotDePasseIncorrectException
     * @param pseudo
     * @param pw
     * @return finalUser
     */
    public User loginUser(String pseudo, String pw) throws PseudoOuMotDePasseIncorrectException {
        User user = getUserByPseudo(pseudo);
        User finalUser;

        if (user.getPassword().equals(pw)){
            finalUser = user;
        } else {
            throw new PseudoOuMotDePasseIncorrectException();
        }
        return finalUser;
    }

    /**
     * Permet de vérifier si le pseudo en paramètre existe dans la base de donnée
     * @param pseudo
     * @return verif : boolean, true si existe sinon false
     */
    public boolean verifUserByPseudo(String pseudo){
        boolean verif = false;
        User user = getUserByPseudo(pseudo);
        if (user != null){
            verif = true;
        }
        return verif;
    }
}