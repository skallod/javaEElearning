/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.actionbazaar.web;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.actionbazaar.buslogic.BidService;
import com.actionbazaar.buslogic.UserService;
import com.actionbazaar.persistence.Bid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bid Manager - handles the add bid form.
 */
@Named
public class BidManager {

    private static final Logger log = LoggerFactory.getLogger(BidManager.class);

    @EJB
    private BidService bidService;

    @EJB
    private UserService userServiceBean;

    private Bid bid = new Bid();

    @Produces
    @Named
    @RequestScoped
    public Bid getBid() {
        return bid;
    }

    @PostConstruct
    void postConstruct(){
        log.info("postConstruct bid sevice "+bidService);
        log.info("postConstruct Bid "+bid);
    }

    public String placeBid() {
        log.info("place bid");
//        bid.setBidder(user);
//        bid.setItem(item);
//        bidService.addBid(bid);
        return "bid_confirm.xhtml";
    }
}
