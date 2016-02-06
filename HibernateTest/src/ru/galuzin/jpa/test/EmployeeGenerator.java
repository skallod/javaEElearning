package ru.galuzin.jpa.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import ru.galuzin.hibernate.test.Address;

import java.util.ArrayList;

/**
 * Created by Galuzin on 01.01.2002.
 */
public class EmployeeGenerator {
    public static void main(String[] args) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(ru.galuzin.jpa.test.Employee.class);
//Create the Address table
        new SchemaExport(config).create(true, true);
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
// Instantiate and Populate the entity Address
        ru.galuzin.jpa.test.Employee employee1 = new ru.galuzin.jpa.test.Employee();
        employee1.setFirstName("Leo");
//addr1.setAddressID(1);
        employee1.setLastName("Galuzin");
        employee1.setManagerName(null);

        Address addr1 = new Address();
        addr1.setStreetAddress("Belova 14");
        addr1.setCity("Msk");
        addr1.setZip("589");
        ArrayList<Address> list1 = new ArrayList<Address>();
        list1.add(addr1);
        employee1.setAddresses(addr1);
        session.save(employee1);
// Save the new entity in the database
        session.getTransaction().commit();
    }
}
