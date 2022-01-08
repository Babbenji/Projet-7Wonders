package services;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import interfaces.exceptions.JoueurDejaDansLaListeDAmisException;
import interfaces.exceptions.JoueurNonExistantException;
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
import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongodbService {

    private MongoDatabase mongoDatabase;
    public static final String NOM_BD = "sevenwonders";

    // connection au serveur mongodb
    MongoClient mongoClient = MongoClients.create(
            new ConnectionString("mongodb+srv://root:root@cluster0.o30qp.mongodb.net/sevenwonders?retryWrites=true&w=majority"));

    /**
     * Construit les services de la database sevenwonders avec POJO
     */
    public MongodbService() {
        //CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register(User.class).build();
        CodecRegistry pojoCodecRegistry =
                fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder()
                                .automatic(true).build()
                        )
                );

        // accès à la database sevenwonders
        this.mongoDatabase = this.mongoClient.getDatabase(this.NOM_BD).withCodecRegistry(pojoCodecRegistry);
    }

    /**
     * Retourner un objet User par rapport à un pseudo en paramètre
     * @return user
     */
    public User getUserByPseudo(String pseudo){
        MongoCollection<User> users = this.mongoDatabase.getCollection("user",User.class);
        return users.find(Filters.eq("pseudonyme", pseudo)).first();
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
        for(User user : getAllUsers()){
            if (user.getPseudo().equals(pseudo)){
                pseudoDejaPrisBoolean = true;
            }
        }
        if (pseudoDejaPrisBoolean == true){
            throw new PseudoDejaPrisException();
        } else {
            Document user = new Document().append("pseudo", pseudo).append("password", pw);
            List<Document>nouveauxAmis = new ArrayList<>();
            user.append("friends", nouveauxAmis);
            users.insertOne(user);
        }
    }

    /**
     * Retourner tous les users
     * @return allUsers
     */
    public Collection<User> getAllUsers(){
        MongoCollection<User> users = this.mongoDatabase.getCollection("user",User.class);
        Collection<User> allUsers = new ArrayList<>();
        users.find(Filters.eq("users",users)).forEach(u -> allUsers.add(u));
        return allUsers;
    }

    /**
     * Retourner une liste de joueurs dans la liste d'ami du joueur dont le pseudo est en paramètre
     * @param pseudo
     * @return friends
     */
    public List<User> getFriendsUser(String pseudo){
        MongoCollection<User> users = this.mongoDatabase.getCollection("user",User.class);
        List<User>friends = new ArrayList<>();
        users.find(Filters.eq("pseudonyme", pseudo)).projection(exclude("friends")).forEach(f->friends.add(f));
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
        MongoCollection<User> users = this.mongoDatabase.getCollection("user",User.class);
        User user = users.find(Filters.eq("pseudonyme", pseudo)).first();
        User newFriend = users.find(Filters.eq("pseudonyme", nouvelAmi)).first();

        if (verifUserByPseudo(nouvelAmi) == true){
            for (User ami : user.getAmis()) {
                if(ami.getPseudo().equals(pseudo)){
                    throw new JoueurDejaDansLaListeDAmisException();
                }
                else {
                    Bson update = set("friends", newFriend); //à modifier !!!!!
                    Bson user1 = (Bson) user;
                    users.updateOne(user1, update);
                }
            }
        } else {
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
        MongoCollection<User> collection = this.mongoDatabase.getCollection("users", User.class);
            Collection<User> users = new ArrayList<>();
            collection.find().forEach((Consumer<? super User>) e -> users.add(e));
            User finalUser = null;

            for (User u : users) {
                if (u.getPassword().equals(pw) && u.getPseudo().equals(pseudo)) {
                    finalUser = u;
                }
                else {
                    throw new PseudoOuMotDePasseIncorrectException();
                }
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