module interfaces {
    requires java.rmi;
    requires model;
    exports interfaces.facade;
    exports interfaces.exceptions;
}