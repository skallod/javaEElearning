package ru.galuzin.payment_system.persistance.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.galuzin.payment_system.common_types.Account;
import ru.galuzin.payment_system.common_types.EntityData;
import ru.galuzin.payment_system.common_types.Operation;
import ru.galuzin.payment_system.common_types.OperationType;
import ru.galuzin.payment_system.common_types.Person;
import ru.galuzin.payment_system.common_types.Terminal;
import ru.galuzin.payment_system.common_types.VersionData;
import ru.galuzin.payment_system.persistance.HibernateSession;

/**
 * Created by User on 23.02.2016.
 */
public class BasicDAOTest {
    Random random = new Random();

    @Before
    public void setUp() throws Exception {

    }
//    @After
//    public void shutdown(){
//        HibernateSession.quit();
//    }

    @Test
    public void createTestBase() throws Exception {

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
//        operation.setSrcAccount(account);
//        operation.setDestAccount(account2);
        operation.setTerminal(terminal);

        //====== операция "положить деньги на счет" ====
        Operation operation2 = new Operation();
        operation2.setMoney(15.5D);
        operation2.setDate(new Date());
        operation2.setOperationType(OperationType.PUT_MONEY.toString());
//        operation2.setSrcAccount(null);
//        operation2.setDestAccount(account);
        operation2.setTerminal(terminal);

        //====== операция "снять деньги со счета" ====
        Operation operation3 = new Operation();
        operation3.setMoney(9.99D);
        operation3.setDate(new Date());
        operation3.setOperationType(OperationType.GET_MONEY.toString());
//        operation3.setSrcAccount(account);
//        operation3.setDestAccount(null);
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


        EntityData ed = new EntityData();
        ed.setUid("uid_1");
        ed.setCreatedBy("leo");
        ed.setEntityType("air");
        ed.setLastVersionCreated(new Date());
        ed.setLastVersionData("last version".getBytes());
        ed.setLastVersionCreatedBy("vad");
        ed.setLastVersionModified(new Date());
        ed.setLastVersionModifiedBy("vad");
        ed.setModifiedBy("kost");
        ed.setVersionsCount(3);
        ed.setLastVersionUid("last_version_uid_1");
        VersionData versionData = new VersionData();
        versionData.setCreated(new Date());
        versionData.setUid(UUID.randomUUID().toString());
        versionData.setEntityData(ed);
        ed.setVersions(new ArrayList<>());
        ed.getVersions().add(versionData);
        basicDAO.addEntity(ed);
//        basicDAO.addEntity(versionData);
    }

    @Test
    public void testUpdateEntity() throws Exception {
        BasicDAO basicDAO = new BasicDAO();
        //final CountDownLatch cdl = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        CountDownLatch cdl1 = new CountDownLatch(2);
        CountDownLatch cdl2 = new CountDownLatch(2);
        Callable<String> callable1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                long nanoTime = System.nanoTime();
                Session sess1 = HibernateSession.getSessionFactory().openSession();
                Transaction tx1 = null;
                try {
                    tx1 = sess1.beginTransaction();
                    Account account = (Account) sess1.get(Account.class, 1L);
                    cdl1.countDown();
                    cdl1.await();
                    double v = random.nextDouble();
                    System.out.println("FIRST v = " + v);
                    account.setMoney(v);
                    System.out.println("FIRST BEFORE UPDATE");
                    sess1.saveOrUpdate(account);
//                    sess1.doWork(new Work() {
//                        @Override
//                        public void execute(Connection connection) throws SQLException {
//                            System.out.println("Transaction isolation level is " + Environment.isolationLevelToString(connection.getTransactionIsolation()));
//                        }
//                    });
                    System.out.println("FIRST AFTER UPDATE");
                    tx1.commit();
                    cdl2.countDown();
                    cdl2.await();
                    System.out.println("FIRST FINISH");
                } catch (Exception e) {
                    e.printStackTrace();
                    if (tx1 != null) {
                        tx1.rollback();
                    }
                } finally {
                    sess1.close();
                }
                return "DONE 1 " + TimeUnit.NANOSECONDS.toMillis(nanoTime);
            }
        };
        Callable<String> callable2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                long nanoTime = System.nanoTime();
                Session sess1 = HibernateSession.getSessionFactory().openSession();
                Transaction tx1 = null;
                try {
                    tx1 = sess1.beginTransaction();
                    Account account = (Account) sess1.get(Account.class, 1L);
                    cdl1.countDown();
                    cdl1.await();
                    double v = random.nextDouble();
                    System.out.println("SECOND v = " + v);
                    account.setMoney(v);
                    System.out.println("SECOND BEFORE UPDATE");
                    sess1.saveOrUpdate(account);
                    System.out.println("SECOND AFTER UPDATE");
                    cdl2.countDown();
                    cdl2.await();
                    tx1.commit();
                    System.out.println("SECOND FINISH");
                } catch (Exception e) {
                    e.printStackTrace();
                    if (tx1 != null) {
                        tx1.rollback();
                    }
                } finally {
                    sess1.close();
                }
                return "DONE 2 " + TimeUnit.NANOSECONDS.toMillis(nanoTime);
            }
        };
        Set<Future<String>> futures = new HashSet<>();
        futures.add(pool.submit(callable1));
        futures.add(pool.submit(callable2));
        for (Future<String> future : futures) {
            System.out.println("future.get() = " + future.get());
        }
        Account acc = (Account) basicDAO.getEntity(Account.class, 1L);
        System.out.println("acc.getMoney() = " + acc.getMoney());
    }

    @Test
    public void test11() {
        BasicDAO basicDAO = new BasicDAO();
        Account a1 = (Account) basicDAO.getEntity(Account.class, 1L);
        Account a2 = (Account) basicDAO.getEntity(Account.class, 1L);
        a1.setMoney(115d);
        basicDAO.updateEntity(a1);
        a2.setMoney(33d);
        basicDAO.updateEntity(a2);
        System.out.println("a1 = " + a1);
        System.out.println("a2 = " + a2);
    }

    @Test
    public void testEntityDataVersion() throws Exception {
        BasicDAO basicDAO = new BasicDAO();
        ExecutorService pool = Executors.newFixedThreadPool(4);
        Set<Future<Void>> futures = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            List<Integer> singl = Collections.singletonList(Integer.valueOf(i));
            Callable<Void> call1 = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    EntityData a1 = (EntityData) basicDAO.getEntity(EntityData.class, "uid_1");
                    //        EntityData a2 = (EntityData)basicDAO.getEntity(EntityData.class, "uid_1");
                    //        a1.setEntityType("water");
                    System.out.println(Thread.currentThread().

                            getName() + " s1 a1.getModified() = " + a1.getModified());
                    Session sess1 = HibernateSession.getSessionFactory().openSession();
                    //        try {
                    //            EntityData entity1 = (EntityData) sess1.merge(a1);
                    //            //Hibernate.initialize(a1.getVersions());
                    //            //EntityData entity1 = (EntityData) sess1.get(EntityData.class, "uid_1");
                    //            System.out.println("a1.getVersions() = " + entity1.getVersions());
                    //            System.out.println(Thread.currentThread().getName()+" s2 a1.getModified() = " + entity1.getModified());
                    //        }finally {
                    //            sess1.close();
                    //        }
                    a1.setEntityType(Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + " s3 a1.getModified() = " + a1.getModified());
                    if(singl.get(0)==1){
                        TimeUnit.SECONDS.sleep(1);
                    }
                    basicDAO.updateEntity(a1);
                    System.out.println(Thread.currentThread().getName() + " s4 a1.getModified() = " + a1.getModified());
                    return null;
                }

            };
            futures.add(pool.submit(call1));
        }
        for (Future f1 :
                futures) {
            System.out.println("f1.get() = " + f1.get());
        }
//        a2.setEntityType("hotel");
//        basicDAO.updateEntity(a2);
//        System.out.println("a1 = " + a1);
//        System.out.println("a2 = " + a2);
    }

    //    @Test
    public void testDeleteEtity() throws Exception {

    }

    //    @Test
    public void testGetEntity() throws Exception {
        BasicDAO basicDAO = new BasicDAO();
        Account account = (Account) basicDAO.getEntity(Account.class, 1L);
        System.out.println("accoutn = " + account.getMoney());

        Session sess = HibernateSession.getSessionFactory().openSession();
        Transaction tx = null;
        Serializable id = null;
        try {
            tx = sess.beginTransaction();
            account = (Account) sess.get(Account.class, 1L);
            System.out.println("account = " + account);
//            for (Operation oper : account.getOperationsSrcAccount()){
//                System.out.println("operSrc = " + oper);
//            }
//            for (Operation oper : account.getOperationsDestAccount()){
//                System.out.println("operDest = " + oper);
//            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            sess.close();
        }

    }

    //    @Test
    public void testGetAllEntities() throws Exception {

    }

    @After
    public void quit() {
        HibernateSession.quit();
    }
}