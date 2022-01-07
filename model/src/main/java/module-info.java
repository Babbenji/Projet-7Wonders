module model {

requires com.fasterxml.jackson.databind;
    requires interfaces;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    exports joueur;
exports cartes;
exports facade;
}