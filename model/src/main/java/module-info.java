module model
{
    requires com.fasterxml.jackson.databind;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    exports joueur;
    exports cartes;
    exports merveilles;
    exports user;
}