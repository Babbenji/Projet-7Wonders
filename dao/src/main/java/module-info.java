module dao {
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.logging;
    requires model;
    exports services.exceptions;
}