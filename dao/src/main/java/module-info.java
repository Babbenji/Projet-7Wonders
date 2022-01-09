module dao {
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.logging;
    requires model;
    requires interfaces;
    exports services.exceptions;
    exports services;
}