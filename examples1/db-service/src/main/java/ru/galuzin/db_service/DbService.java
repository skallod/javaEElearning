package ru.galuzin.db_service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import ru.galuzin.model.Account;
import ru.galuzin.model.Role;

public interface DbService {

    void saveAccount(Account account) throws SQLException;
    /**
     *
     * @param email
     * @param pass
     * @return - account uid
     * @throws SQLException
     */
    Optional<String> isAccountExist(String email, byte[] pass) throws SQLException;

    public boolean isEmailExist(String email) throws SQLException;
    /**
     *
     * @param accountId
     * @return
     */
    Set<Role> getRoles(String accountId) throws SQLException;

    void saveRole(String accountUid, Role role) throws SQLException;

    void saveAccountWithRole(Account account, Role role) throws SQLException;

    void shutdown();
}
