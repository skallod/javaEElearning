///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.actionbazaar.buslogic;
//
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.UserTransaction;
//import java.math.BigDecimal;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//
//import com.actionbazaar.State;
//import com.actionbazaar.account.Address;
//import com.actionbazaar.account.Bidder;
//import com.actionbazaar.buslogic.exceptions.CreditCardSystemException;
//import com.actionbazaar.model.Bid;
//import com.actionbazaar.model.Item;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.asset.StringAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
///**
// * Tests the BidManager
// */
//@RunWith(Arquillian.class)
//public class BidManagerTest {
//
//    /**
//     * Entity Manager
//     */
//    @PersistenceContext
//    private EntityManager em;
//
//    /**
//     * User Transaction
//     */
//    @Inject
//    private UserTransaction utx;
//
//    private static final String APPLICATION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//            + "<application xmlns=\"http://java.sun.com/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/application_6.xsd\" version=\"6\">"            + "<display-name>org.acme.project</display-name>"
//            // the WAR must be added to the application.xml !
//            + "<module><web><web-uri>test.war</web-uri><context-root>/test</context-root></web></module>"
//            + "</application>";
//
//    @Deployment
//    public static Archive<?> createDeployment() {
//        WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war")
//            .addPackage(State.class.getPackage())
//            .addPackage(Address.class.getPackage())
//            .addPackage(BidManager.class.getPackage())
//            .addPackage(CreditCardSystemException.class.getPackage())
//            .addPackage(Bid.class.getPackage())
//            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
//            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//        .setWebXML(new StringAsset(APPLICATION_XML));
//        System.out.println(wa.toString(true));
//        return wa;
//    }
//
//    /**
//     * Starts the transaction
//     * @throws Exception - exception
//     */
//    @Before
//    public void startTransaction() throws Exception {
//        utx.begin();
//        em.joinTransaction();
//    }
//
//    /**
//     * Commits the transaction
//     * @throws Exception - exception
//     */
//    @After
//    public void commitTransaction() throws Exception {
//        utx.commit();
//    }
//
//    /**
//     * Tests creating and persisting an Item
//     */
//    @Test
//    public void testCreates() {
//        /**
//         * Create an Item and persist it
//         */
//        Calendar endDate = new GregorianCalendar(2013,1,1,12,0);
//        Item item = new Item("Hobie Holder 14", new Date(),endDate.getTime(), new Date(), new BigDecimal("14"));
//        Address address = new Address("street","city",State.California,"90210","555-555-5555");
//        em.persist(item);
//
//        /**
//         * Create a bidder and persist it
//         */
//        Bidder bidder = new Bidder("rcuprak","password","Ryan","Cuprak",address,new Date(),false);
//        em.persist(bidder);
//
//        /**
//         * Create a bid on the item
//         */
//        Bid bid = new Bid(bidder,item,new BigDecimal("100"));
//        em.persist(bid);
//
//    }
//}
