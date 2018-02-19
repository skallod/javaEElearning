package ru.galuzin.payment_system.persistance;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.junit.Test;
import ru.galuzin.hb_pool_impl.C3p0pool;
import ru.galuzin.payment_system.common_types.DictionaryData;

public class C3p0HibernateTest {
    @Test
    public void test() throws Exception{
        Logger log = Logger.getLogger("c3test");
        C3p0pool c3p0pool = new C3p0pool();
        long t = System.nanoTime();
        Transaction tx = null;
        Session sess = null;
        try {
            sess = HibernateSession.getSessionFactory().openSession();
            tx = sess.beginTransaction();
            Criteria criteria = sess.createCriteria(DictionaryData.class);
            criteria.addOrder(Order.desc("modified"));
            criteria.setMaxResults(50);
            List<DictionaryData> list = (List<DictionaryData>) criteria.list();
            list.forEach(dd -> System.out.println("; " + dd.getUid()));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e1) {
                    System.out.println("rollback exp " + e1.getMessage());
                }
            }
            e.printStackTrace();
        } finally {
            sess.close();
            System.out.println("timing = " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t));
        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
