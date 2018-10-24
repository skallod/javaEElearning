package ru.galuzin.db_service.dao;

import ru.galuzin.db_service.connection.DataSource;
import ru.galuzin.db_service.executor.PreparedExecutor;
import ru.galuzin.db_service.executor.TransactionExecutor;
import ru.galuzin.db_service.executor.TransactionContext;
import ru.galuzin.model.Account;
import ru.galuzin.model.Role;
import ru.galuzin.model.ValueHolder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

class AccountDAO implements AccountDAOService{

    private final TransactionExecutor txExecutor;

    private final PreparedExecutor preparedExecutor;

    AccountDAO(DataSource dataSource){
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
        preparedExecutor.execUpdate("insert into accounts" +
                    "(account_uid,account_name,account_email,account_pass)" +
                    " values(?,?,?,?);",(st)->{
                st.setString(1,account.getUid());
                st.setString(2,account.getName());
                st.setString(3,account.getEmail());
                st.setBytes(4,account.getPassword());
            },txContext);
    }

    @Override
    public Optional<String> isAccountExist(String email, byte[] pass) throws SQLException {
        //select exists(select
        //select 1 from tbl where userid = 123 limit 1;
        ValueHolder<List> vh = new ValueHolder<>(Collections.emptyList());
        txExecutor.exec((txContext)->{
                vh.setValue(preparedExecutor.execQuery("select account_uid from accounts " +
                        "where account_email=? and account_pass=? limit 1",
                (st) -> {
                    st.setString(1, email);
                    st.setBytes(2, pass);
                },
                (rs) -> rs.getString("account_uid"),txContext));
        });
        return vh.getValue().isEmpty()?Optional.empty():vh.getValue().stream().findAny();
    }

    @Override
    public boolean isEmailExist(String email) throws SQLException {
        ValueHolder<List<Integer>> existVh = new ValueHolder<>(Collections.emptyList());
        txExecutor.exec((txContext) ->
                {
                    List<Integer> exist = preparedExecutor.execQuery("select 1 from accounts " +
                                    "where account_email=? limit 1",
                            (st) -> {
                                st.setString(1, email);
                            },
                            (rs) -> rs.getInt(1), txContext);
                    existVh.setValue(exist);
                }
        );
        return !existVh.getValue().isEmpty();
    }

    @Override
    public Set<Role> getRoles(String accountUid) throws SQLException{
        ValueHolder<List<Role>> rolesVh = new ValueHolder<>(Collections.emptyList());
        txExecutor.exec((txContext)->{
            List<Role> roles = preparedExecutor.execQuery("select role_id from user_roles" +
                            " where account_uid=?;",
                    (st) -> st.setString(1, accountUid),
                    (rs) -> Role.getByCode(rs.getInt("role_id")),txContext);
            rolesVh.setValue(roles);
        });
        return (rolesVh.getValue().isEmpty())?Collections.emptySet() : new HashSet<Role>(rolesVh.getValue());
    }
    @Override
    public void saveRole(String accountUid, Role role) throws SQLException {
        txExecutor.exec((txContext)->{
            saveRole(accountUid, role,txContext);
        });
    }

    private void saveRole(String accountUid, Role role, TransactionContext txContext) throws SQLException {
        preparedExecutor.execUpdate("insert into user_roles(account_uid,role_id) " +
                    "values(?,?);",
            (st) -> {
                st.setString(1,accountUid);
                st.setInt(2,role.getCode());
            },txContext);
    }

    @Override
    public void saveAccountWithRole(Account account, Role role) throws SQLException{
        txExecutor.exec((txContext)->{
            saveAccount(account,txContext);
            saveRole(account.getUid(),role,txContext);}
        );
    }
}
