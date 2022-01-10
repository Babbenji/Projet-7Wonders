module rmiService {
    requires java.rmi;
    requires model;
    requires plateau;
    requires dao;
    exports app;
    exports service;
}