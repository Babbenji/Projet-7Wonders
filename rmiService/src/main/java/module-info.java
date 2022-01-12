module rmiService {
    requires java.rmi;
    requires model;
    requires plateau;
    requires dao;
    requires org.mongodb.bson;
    exports app;
    exports service;
}