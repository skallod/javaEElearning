package ru.galuzin.db_service;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.model.Account;
import ru.galuzin.model.Role;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DbServiceTest {

    private static final Logger log = LoggerFactory.getLogger(DbServiceTest.class);

    static DbServiceImpl dbService;

    @BeforeClass
    public static void beforeClass(){
        dbService = new DbServiceImpl(new DataSourceTest());
    }

    @Test
    public void shouldCreateAccount() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Account account = new Account(
                uuid,
                "temp@temp.ru",
                "temp",
                HashUtil.hash("password".getBytes(StandardCharsets.UTF_8))
                );
        dbService.saveAccount(account);
        isAccountExistTest(uuid);
        getRolesTest(uuid);
    }

    void isAccountExistTest(String uuid) throws Exception{
        Optional<String> accountExist = dbService.isAccountExist("temp@temp.ru"
                , HashUtil.hash("password".getBytes(StandardCharsets.UTF_8)));
        assertThat(accountExist.isPresent(),is(true));
        assertThat(accountExist.get(),is(uuid));
    }

    void getRolesTest(String uuid) throws SQLException {
        dbService.saveRole(uuid, Role.ADMIN);
        Set<Role> roles = dbService.getRoles(uuid);
        assertThat(roles.stream().findAny().get(),is(Role.ADMIN));
    }
    @Test
    public void saveAccountWithRole() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Account account = new Account(
                uuid,
                "tt@tt.ru",
                "tt",
                HashUtil.hash("tt".getBytes(StandardCharsets.UTF_8))
        );
        dbService.saveAccountWithRole(account, Role.USER);
        Set<Role> roles = dbService.getRoles(uuid);
        assertThat(roles.stream().findAny().get(), is(Role.USER));
    }
    @Test(expected = SQLException.class)
    public void shouldException() throws Exception{
        dbService.saveRole(UUID.randomUUID().toString(), Role.USER);
    }

    @AfterClass
    public static void afterClass(){
        dbService.shutdown();
    }

}
