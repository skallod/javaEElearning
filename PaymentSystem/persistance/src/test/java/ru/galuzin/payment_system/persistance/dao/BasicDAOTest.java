package ru.galuzin.payment_system.persistance.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.galuzin.payment_system.common_types.*;
import ru.galuzin.payment_system.persistance.HibernateSession;

import java.io.Serializable;
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

        BasicDAO basicDAO = new BasicDAO();

        Account account = new Account();
        account.setMoney(10D);
        Account account2 = new Account();
        account2.setMoney(10D);

        Person person = new Person();
        person.setAddress("Spb");
        person.setLastName("Repa");
        person.setName("Leo");
        Person person2 = new Person();
        person2.setAddress("Zeon");
        person2.setLastName("Loop");
        person2.setName("Den");

        account.setPerson(person);
        account2.setPerson(person2);

        Terminal terminal = new Terminal();
        terminal.setAddress("Generala belogo 14");

        //====== операция "перевод денег" ======
        Operation operation = new Operation();
        operation.setMoney(5D);
        operation.setDate(new Date());
        operation.setOperationType(OperationType.MONEY_ORDER.toString());
        operation.setSrcAccount(account);
        operation.setDestAccount(account2);
        operation.setTerminal(terminal);

        //====== операция "положить деньги на счет" ====
        Operation operation2 = new Operation();
        operation2.setMoney(15.5D);
        operation2.setDate(new Date());
        operation2.setOperationType(OperationType.PUT_MONEY.toString());
        operation2.setSrcAccount(null);
        operation2.setDestAccount(account);
        operation2.setTerminal(terminal);

        //====== операция "снять деньги со счета" ====
        Operation operation3 = new Operation();
        operation3.setMoney(9.99D);
        operation3.setDate(new Date());
        operation3.setOperationType(OperationType.GET_MONEY.toString());
        operation3.setSrcAccount(account);
        operation3.setDestAccount(null);
        operation3.setTerminal(terminal);

        basicDAO.addEntity(person);
        basicDAO.addEntity(person2);
        basicDAO.addEntity(account);
        basicDAO.addEntity(account2);
        basicDAO.addEntity(terminal);
        basicDAO.addEntity(operation);
        basicDAO.addEntity(operation2);
        basicDAO.addEntity(operation3);

        System.out.println(account);

    }

    @Test
    public void testUpdateEntity() throws Exception {

    }

    @Test
    public void testDeleteEtity() throws Exception {

    }

    @Test
    public void testGetEntity() throws Exception {
        BasicDAO basicDAO = new BasicDAO();
        Account account = (Account)basicDAO.getEntity(Account.class, 1L);
        System.out.println("accoutn = " + account.getMoney());

        Session sess = HibernateSession.getSessionFactory().openSession();
        Transaction tx=null;
        Serializable id=null;
        try {
            tx = sess.beginTransaction();
            account = (Account)sess.get(Account.class, 1L);
            System.out.println("account = " + account);
            for (Operation oper : account.getOperationsSrcAccount()){
                System.out.println("operSrc = " + oper);
            }
            for (Operation oper : account.getOperationsDestAccount()){
                System.out.println("operDest = " + oper);
            }
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw new RuntimeException(e);
        }
        finally {
            sess.close();
        }

    }

    @Test
    public void testGetAllEntities() throws Exception {

    }
    @After
    public void quit(){
        HibernateSession.quit();
    }
}