module interfaces
{
    requires java.rmi;
    requires dao;
    exports interfaces.facade;
    exports interfaces.exceptions;
    exports interfaces.type;
}