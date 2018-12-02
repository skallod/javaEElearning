package ru.galuzin.db_service;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.domain.Account;
import ru.galuzin.domain.Role;

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
        Account account = new Account(
                "temp@temp.ru",
                "temp",
                HashUtil.hash("password".getBytes(StandardCharsets.UTF_8))
                );
        dbService.saveAccount(account);
        isAccountExistTest(account.getId());
        getRolesTest(account.getId());
    }

    void isAccountExistTest(Long uuid) throws Exception{
        Optional<Long> accountExist = dbService.isAccountExist("temp@temp.ru"
                , HashUtil.hash("password".getBytes(StandardCharsets.UTF_8)));
        assertThat(accountExist.isPresent(),is(true));
        assertThat(accountExist.get(),is(uuid));
    }

    void getRolesTest(Long uuid) throws SQLException {
        dbService.saveRole(uuid, Role.ADMIN);
        Set<Role> roles = dbService.getRoles(uuid);
        assertThat(roles.stream().findAny().get(),is(Role.ADMIN));
    }
    @Test
    public void saveAccountWithRole() throws Exception {
        Account account = new Account(
                "tt@tt.ru",
                "tt",
                HashUtil.hash("tt".getBytes(StandardCharsets.UTF_8))
        );
        dbService.saveAccountWithRole(account, Role.USER);
        Set<Role> roles = dbService.getRoles(account.getId());
        assertThat(roles.stream().findAny().get(), is(Role.USER));
    }
    @Test(expected = SQLException.class)
    public void shouldException() throws Exception{
        dbService.saveRole(111L, Role.USER);
    }

    @AfterClass
    public static void afterClass(){
        dbService.shutdown();
    }

}
