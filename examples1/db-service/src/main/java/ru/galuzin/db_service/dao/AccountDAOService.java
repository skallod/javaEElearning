package ru.galuzin.db_service.dao;

import ru.galuzin.domain.Account;
import ru.galuzin.domain.Role;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface AccountDAOService {
    void saveAccount(Account account) throws SQLException;
    Optional<Long> isAccountExist(String email, byte[] pass) throws SQLException;
    boolean isEmailExist(String email) throws SQLException;
    Set<Role> getRoles(Long accountUid) throws SQLException;
    void saveRole(Long accountUid, Role role) throws SQLException;
    void saveAccountWithRole(Account account, Role role) throws SQLException;
}
