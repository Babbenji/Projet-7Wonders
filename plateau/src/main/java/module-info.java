module plateau {
    requires com.fasterxml.jackson.databind;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    exports facade;
    exports exceptions;
}