package ru.galuzin.payment_system.persistance.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.galuzin.payment_system.common_types.*;
import ru.galuzin.payment_system.persistance.HibernateSession;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by User on 23.02.2016.
 */
public class BasicDAOTest {

    @Before
    public void setUp() throws Exception {

    }
//    @After
//    public void shutdown(){
//        HibernateSession.quit();
//    }

    @Test
    public void testAddEntity() throws Exception {
        Account account = new Account();
        account.setMoney(10D);
        BasicDAO basicDAO = new BasicDAO();
        Person person = new Person();
        person.setAddress("Spb");
        person.setLastName("Repa");
        person.setName("Leo");
        account.setPerson(person);
        Terminal terminal = new Terminal();
        terminal.setAddress("Generala belogo 14");
        Operation operation = new Operation();
        operation.setMoney(5D);
        operation.setDate(new Date());
        operation.setOperationType(OperationType.PUT_MONEY.toString());
        operation.setDestAccount(account);
        operation.setTerminal(terminal);

        basicDAO.addEntity(person);
        basicDAO.addEntity(account);
        basicDAO.addEntity(terminal);
        basicDAO.addEntity(operation);

    }

    @Test
    public void testUpdateEntity() throws Exception {

    }

    @Test
    public void testDeleteEtity() throws Exception {

    }

    @Test
    public void testGetEntity() throws Exception {

    }

    @Test
    public void testGetAllEntities() throws Exception {

    }
}