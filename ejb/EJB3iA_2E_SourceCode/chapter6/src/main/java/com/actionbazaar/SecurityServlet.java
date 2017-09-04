package com.actionbazaar;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.actionbazaar.account.Address;
import com.actionbazaar.account.Bidder;
import com.actionbazaar.buslogic.BidManager;
import com.actionbazaar.model.Bid;
import com.actionbazaar.model.CreditCard;
import com.actionbazaar.model.CreditCardType;
import com.actionbazaar.model.Item;

@WebServlet(name = "SecurityEJBServlet", urlPatterns = {"/securityEJBServlet"})
public class SecurityServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger("SecurityServlet");

    @EJB(beanName = "BidManagerBMT")
    private BidManager ejb;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("User: {}"+ req);
        try {
            utx.begin();
            em.joinTransaction();
            /**
             //         * Create an Item and persist it
             //         */
            Calendar endDate = new GregorianCalendar(2013, 1, 1, 12, 0);
            Item item = new Item("Hobie Holder 14", new Date(), endDate.getTime(), new Date(), new BigDecimal("14"));
            Address address = new Address("street", "city", State.California, "90210", "555-555-5555");
            em.persist(item);

            /**
             * Create a bidder and persist it
             */
            Bidder bidder = new Bidder("rcuprak", "password", "Ryan", "Cuprak", address, new Date(), false);
            em.persist(bidder);

            /**
             * Create a bid on the item
             */
            Bid bid = new Bid(bidder, item, new BigDecimal("100"));
            em.persist(bid);

            CreditCard creditCard = new CreditCard();
            creditCard.setAccountNumber("2345 6756 5423");
            creditCard.setCreditCardType(CreditCardType.MASTERCARD);
            creditCard.setExpirationMonth("10");
            creditCard.setExpirationYear("2020");
            creditCard.setNameOnCard("IVAN IVANOV");
            creditCard.setSecurityCode("231");
            em.persist(creditCard);

            utx.commit();

            ejb.placeSnagItOrder(item,bidder,creditCard);
            ejb.cancelBid(bid);

        }catch (Exception e){
            log.log(Level.SEVERE,"", e);
        }
    }
}