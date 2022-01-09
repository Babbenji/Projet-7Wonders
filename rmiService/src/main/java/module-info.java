module rmiService {
    requires java.rmi;
    requires model;
    requires plateau;
    requires dao;
    exports application;
    exports service;
}