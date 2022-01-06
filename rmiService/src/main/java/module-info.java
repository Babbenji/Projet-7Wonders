module rmiService {
    requires java.rmi;
    requires model;
    requires interfaces;
    exports application;
    exports service;
}