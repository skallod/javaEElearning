package ru.galuzin.db_service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.model.Account;
import ru.galuzin.model.Role;

public class DbServiceImpl implements DbService{

    private static final Logger log = LoggerFactory.getLogger(DbServiceImpl.class);

    private static final Map<Role,Integer> rolesMap = Collections.unmodifiableMap(new HashMap<Role,Integer>(){{
        put(Role.ADMIN,1);
        put(Role.USER,2);
    }});

    private final DataSource dataSource;

    private final PreparedExecutor preparedExecutor;

    public DbServiceImpl(DataSource dataSource){
        this.dataSource = dataSource;
        this.preparedExecutor = new PreparedExecutor(dataSource);
    }

    @Override
    public void saveAccount(Account account) throws SQLException {
        saveAccount(account,null);
    }

    void saveAccount(Account account, Connection conn) throws SQLException {
        preparedExecutor.execUpdate("insert into accounts" +
                "(account_uid,account_name,account_email,account_pass)" +
                " values(?,?,?,?);",(st)->{
            st.setString(1,account.getUid());
            st.setString(2,account.getName());
            st.setString(3,account.getEmail());
            st.setBytes(4,account.getPassword());
        },conn);
    }

    @Override
    public Optional<String> isAccountExist(String email, byte[] pass) throws SQLException {
        //select exists(select
        //select 1 from tbl where userid = 123 limit 1;
        List<String> account_uid = preparedExecutor.execQuery("select account_uid from accounts " +
                        "where account_email=? and account_pass=? limit 1",
                (st) -> {
                    st.setString(1, email);
                    st.setBytes(2, pass);
                },
                (rs) -> rs.getString("account_uid"));
        return account_uid.isEmpty()?Optional.empty():account_uid.stream().findAny();
    }

    @Override
    public boolean isEmailExist(String email) throws SQLException {
        List<Integer> exists = preparedExecutor.execQuery("select 1 from accounts " +
                        "where account_email=? limit 1",
                (st) -> {
                    st.setString(1, email);
                },
                (rs) -> rs.getInt(1));
        return !exists.isEmpty();
    }

    @Override
    public Set<Role> getRoles(String accountUid) throws SQLException{
        List<Role> roles = preparedExecutor.execQuery("select role_name from user_roles ur join roles r " +
                        "on ur.role_id=r.role_id where account_uid=?;",
                (st) -> st.setString(1, accountUid),
                (rs) -> Role.valueOf(rs.getString("role_name")));
        return new HashSet<Role>(roles);
    }

    public void saveRole(String accountUid, Role role) throws SQLException {
        saveRole(accountUid,role,null);
    }

    void saveRole(String accountUid, Role role, Connection conn) throws SQLException{
        preparedExecutor.execUpdate("insert into user_roles(account_uid,role_id) " +
                        "values(?,?);",
                (st) -> {
                    st.setString(1,accountUid);
                    st.setInt(2,rolesMap.get(role));
                },conn);
    }

    public void saveAccountWithRole(Account account, Role role) throws SQLException{
        try(Connection connection = getConnection()){
            saveAccount(account,connection);
            saveRole(account.getUid(),role,connection);
            connection.commit();
        }
    }

    Connection getConnection() throws SQLException {
        return dataSource.getConnection();
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
