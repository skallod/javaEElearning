package ru.galuzin.db_service.executor;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

@Setter
@Getter
public class TransactionContext {

    private Connection connection;

    //must be package access
    TransactionContext(){
    }
}
