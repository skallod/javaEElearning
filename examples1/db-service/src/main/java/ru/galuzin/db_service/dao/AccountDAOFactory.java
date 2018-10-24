package ru.galuzin.db_service.dao;

import ru.galuzin.db_service.connection.DataSource;

public class AccountDAOFactory {
    public static AccountDAOService createAccountDAOService(DataSource dataSource){
        return new AccountDAO(dataSource);
    }
}
