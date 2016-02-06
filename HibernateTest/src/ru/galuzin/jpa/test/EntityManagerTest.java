package ru.galuzin.jpa.test;

import ru.galuzin.jpa.test.Employee;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

/**
 * Created by Galuzin on 07.07.2015.
 */
public class EntityManagerTest {

    EntityManager em;

    EntityManagerFactory factory;
    private static final String PERSISTENCE_CONTEXT_NAME = "employees";

    @Resource
    UserTransaction userTransaction;

    public void test() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_CONTEXT_NAME);
        EntityManager em = factory.createEntityManager();
        em = factory.createEntityManager();
        Employee newEmployee = new Employee();
        newEmployee.firstName="Mary";
        newEmployee.lastName="Thompson";
//        newEmployee.setEname("Mary Thompson");
        try {
            userTransaction.begin();
            em.persist(newEmployee);
//            em.remove(oldEmployee);
            userTransaction.commit();
        } catch (SystemException e) { //several other exceptions can be thrown here
            e.printStackTrace();
            try {
                userTransaction.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EntityManagerTest().test();
    }
}
