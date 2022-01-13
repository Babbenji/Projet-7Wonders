module model
{
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires java.desktop;
    requires javafx.graphics;
    exports joueur;
    exports cartes;
    exports merveilles;
    exports user;
    exports partie;
    exports type;
}