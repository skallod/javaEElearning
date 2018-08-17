package ru.galuzin.db_service;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import ru.galuzin.model.Account;
import ru.galuzin.model.Role;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DbServiceTest {

    @Test
    public void shouldCreateAccount() throws Exception {
        DbService dbService = new DbServiceImpl();
        Account account = new Account(
                "cbe39ff4-7c38-4f29-bd39-75a057042234",
                "temp@temp.ru",
                "temp",
                HashUtil.hash("password".getBytes(StandardCharsets.UTF_8))
                );
        dbService.saveAccount(account);
        isAccountExistTest();
        getRolesTest();
    }

    public void isAccountExistTest() throws Exception{
        DbService dbService = new DbServiceImpl();
        Optional<String> accountExist = dbService.isAccountExist("temp@temp.ru"
                , HashUtil.hash("password".getBytes(StandardCharsets.UTF_8)));
        assertThat(accountExist.isPresent(),is(true));
        assertThat(accountExist.get(),is("cbe39ff4-7c38-4f29-bd39-75a057042234"));
    }

    public void getRolesTest() throws SQLException {
        DbService dbService = new DbServiceImpl();
        try {
            dbService.saveRole("cbe39ff4-7c38-4f29-bd39-75a057042234", Role.ADMIN);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("already exist");
        }
        Set<Role> roles = dbService.getRoles("cbe39ff4-7c38-4f29-bd39-75a057042234");
        assertThat(roles.stream().findAny().get(),is(Role.ADMIN));
    }


}
