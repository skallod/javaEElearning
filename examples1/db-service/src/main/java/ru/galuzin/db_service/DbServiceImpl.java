package ru.galuzin.db_service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import ru.galuzin.db_service.connection.DataSource;
import ru.galuzin.db_service.dao.AccountDAO;
import ru.galuzin.db_service.dao.AccountDAOService;
import ru.galuzin.domain.Account;
import ru.galuzin.domain.Role;
@Slf4j
public class DbServiceImpl implements DbService{

    private final DataSource dataSource;

    private final AccountDAOService accountDAO;

    public DbServiceImpl(DataSource dataSource){
        this.dataSource = dataSource;
        accountDAO = new AccountDAO(dataSource);
    }

    @Override
    public void saveAccount(Account account) throws SQLException {
        accountDAO.saveAccount(account);
    }

    @Override
    public Optional<Long> isAccountExist(String email, byte[] pass) throws SQLException {
        return accountDAO.isAccountExist(email,pass);
    }

    @Override
    public boolean isEmailExist(String email) throws SQLException {
        return accountDAO.isEmailExist(email);
    }

    @Override
    public Set<Role> getRoles(Long accountUid) throws SQLException{
        return accountDAO.getRoles(accountUid);
    }

    @Override
    public void saveRole(Long accountUid, Role role) throws SQLException {
        accountDAO.saveRole(accountUid,role);
    }

    @Override
    public void saveAccountWithRole(Account account, Role role) throws SQLException {
        accountDAO.saveAccountWithRole(account,role);
    }


    @Override
    public void shutdown() {
        try {
            dataSource.shutdown();
        }catch (Exception e){
            log.error("data source shutdown");
        }
    }
}
