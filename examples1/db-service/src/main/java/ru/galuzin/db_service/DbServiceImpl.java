package ru.galuzin.db_service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import ru.galuzin.db_service.connection.DataSource;
import ru.galuzin.db_service.dao.AccountDAOFactory;
import ru.galuzin.db_service.dao.AccountDAOService;
import ru.galuzin.model.Account;
import ru.galuzin.model.Role;
@Slf4j
public class DbServiceImpl implements DbService{

    private final DataSource dataSource;

    private final AccountDAOService accountDAO;

    public DbServiceImpl(DataSource dataSource){
        this.dataSource = dataSource;
        accountDAO = AccountDAOFactory.createAccountDAOService(dataSource/*,preparedExecutor*/);
    }

    @Override
    public void saveAccount(Account account) throws SQLException {
        accountDAO.saveAccount(account);
    }

    @Override
    public Optional<String> isAccountExist(String email, byte[] pass) throws SQLException {
        return accountDAO.isAccountExist(email,pass);
    }

    @Override
    public boolean isEmailExist(String email) throws SQLException {
        return accountDAO.isEmailExist(email);
    }

    @Override
    public Set<Role> getRoles(String accountUid) throws SQLException{
        return accountDAO.getRoles(accountUid);
    }

    @Override
    public void saveRole(String accountUid, Role role) throws SQLException {
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
