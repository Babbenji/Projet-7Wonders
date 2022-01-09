module model
{
    requires com.fasterxml.jackson.databind;
    requires interfaces;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires dao;
    exports joueur;
    exports cartes;
    exports merveilles;
    exports facade;
    exports user;
}