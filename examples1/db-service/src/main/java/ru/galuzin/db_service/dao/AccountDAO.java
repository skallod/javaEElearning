package ru.galuzin.db_service.dao;

import ru.galuzin.db_service.connection.DataSource;
import ru.galuzin.db_service.executor.PreparedExecutor;
import ru.galuzin.db_service.executor.TransactionExecutor;
import ru.galuzin.db_service.executor.TransactionContext;
import ru.galuzin.domain.Account;
import ru.galuzin.domain.Role;

import java.sql.SQLException;
import java.util.*;

public class AccountDAO implements AccountDAOService{

    private final TransactionExecutor txExecutor;

    private final PreparedExecutor preparedExecutor;

    public AccountDAO(DataSource dataSource){
        txExecutor = new TransactionExecutor(dataSource);
        this.preparedExecutor = new PreparedExecutor();
    }

    @Override
    public void saveAccount(Account account) throws SQLException {
        txExecutor.exec((tContext)->{
            saveAccount(account,tContext);
        });
    }

    void saveAccount(Account account, TransactionContext txContext) throws SQLException {
        List<Long> accountIdList= preparedExecutor.execQuery("select nextval(?);",
                (st)->st.setString(1,"account_id_seq"),
                (ri)->ri.getLong(1),txContext);
        Long accountID = accountIdList.get(0);
        preparedExecutor.execUpdate("insert into accounts" +
                        "(account_id,account_name,account_email,account_pass)" +
                        " values(?,?,?,?);"/* returning account_id;"*/, (st) -> {
                    st.setLong(1, accountID);
                    st.setString(2, account.getName());
                    st.setString(3, account.getEmail());
                    st.setBytes(4, account.getPassword());
                    //select lastval();
                },
                 txContext);
//        List<Long> accountIdList = preparedExecutor.execQuery("select lastval() as account_id;"
//                ,(st)->{},(rs) -> rs.getLong("account_id"),txContext);
        account.setId(accountID);
    }

    @Override
    public Optional<Long>/*todo tuple*/ isAccountExist(String email, byte[] pass) throws SQLException {
         List<Long> result = txExecutor.exec((txContext)->{
            return  preparedExecutor.execQuery("select account_id,account_name from accounts " +
                            "where account_email=? and account_pass=? limit 1",
                    (st) -> {
                        st.setString(1, email);
                        st.setBytes(2, pass);
                    },
                    (rs) -> rs.getLong("account_id"), txContext);
        });
        return result.isEmpty()?Optional.empty():result.stream().findAny();
    }

    @Override
    public boolean isEmailExist(String email) throws SQLException {
        List<Integer> exist = txExecutor.exec((txContext) ->
                {
                    return preparedExecutor.execQuery("select 1 from accounts " +
                                    "where account_email=? limit 1",
                            (st) -> {
                                st.setString(1, email);
                            },
                            (rs) -> rs.getInt(1), txContext);
                }
        );
        return !exist.isEmpty();
    }

    @Override
    public Set<Role> getRoles(Long accountUid) throws SQLException{
        List<Role> roles = txExecutor.exec((txContext)->{
            return preparedExecutor.execQuery("select role_id from user_roles" +
                            " where account_id=?;",
                    (st) -> st.setLong(1, accountUid),
                    (rs) -> Role.getByCode(rs.getInt("role_id")),txContext);
        });
        return (roles.isEmpty())?Collections.emptySet() : new HashSet<Role>(roles);
    }

    @Override
    public void saveRole(Long accountUid, Role role) throws SQLException {
        txExecutor.exec((txContext)->{
            saveRole(accountUid, role,txContext);
        });
    }

    private void saveRole(Long accountUid, Role role, TransactionContext txContext) throws SQLException {
        preparedExecutor.execUpdate("insert into user_roles(account_id,role_id) " +
                    "values(?,?);",
            (st) -> {
                st.setLong(1,accountUid);
                st.setInt(2,role.getCode());
            },txContext);
    }

    @Override
    public void saveAccountWithRole(Account account, Role role) throws SQLException{
        txExecutor.exec((txContext)->{
            saveAccount(account,txContext);
            saveRole(account.getId(),role,txContext);}
        );
    }
}
