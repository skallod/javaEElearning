package ru.galuzin.db_service.dao;

import ru.galuzin.model.Account;
import ru.galuzin.model.Role;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface AccountDAOService {
    void saveAccount(Account account) throws SQLException;
    Optional<String> isAccountExist(String email, byte[] pass) throws SQLException;
    boolean isEmailExist(String email) throws SQLException;
    Set<Role> getRoles(String accountUid) throws SQLException;
    void saveRole(String accountUid, Role role) throws SQLException;
    void saveAccountWithRole(Account account, Role role) throws SQLException;
}
