package ru.galuzin.db_service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import ru.galuzin.domain.Account;
import ru.galuzin.domain.Role;

public interface DbService {

    void saveAccount(Account account) throws SQLException;
    /**
     *
     * @param email
     * @param pass
     * @return - account id
     * @throws SQLException
     */
    Optional<Long> isAccountExist(String email, byte[] pass) throws SQLException;

    public boolean isEmailExist(String email) throws SQLException;
    /**
     *
     * @param accountId
     * @return
     */
    Set<Role> getRoles(Long accountId) throws SQLException;

    void saveRole(Long accountUid, Role role) throws SQLException;

    void saveAccountWithRole(Account account, Role role) throws SQLException;

    void shutdown();
}
