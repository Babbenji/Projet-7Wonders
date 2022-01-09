module plateau {
    requires com.fasterxml.jackson.databind;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires model;
    requires dao;
    requires java.rmi;

    exports facade;
    exports exceptions;
}