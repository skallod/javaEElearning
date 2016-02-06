package ru.galuzin.hibernate.test;

import java.util.List;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class AddressCreator {
    public static void main(String[] args) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Address.class);
//Create the Address table
//        new SchemaExport(config).create(true, true);
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
// Instantiate and Populate the entity Address
        Address addr1 = new Address();
        addr1.setStreetAddress("Nevskiy 12");
//addr1.setAddressID(1);
        addr1.setCity("Spb");
        addr1.setZip("3202");
        session.save(addr1);
// Save the new entity in the database
        session.getTransaction().commit();
    }
}
