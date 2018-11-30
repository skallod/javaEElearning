package ru.galuzin.generics3;

public class TransactionContext {
    String connectionID;
    TransactionContext(String connectionID){
        this.connectionID = connectionID;
    }

    public String getConnectionID() {
        return connectionID;
    }
}
