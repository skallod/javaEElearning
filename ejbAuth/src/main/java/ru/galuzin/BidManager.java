package ru.galuzin;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by galuzin on 22.11.2016.
 */
//@Named
//@RequestScoped
//public class BidManager {
//    @Inject
//    private BidService bidService;
//    @Inject
//    private User user;
//    @Inject
//    private Item item;
//    private Bid bid = new Bid();
//    @Produces
//    @Named
//    @RequestScoped
//    public Bid getBid() {
//        return bid;
//    }
//    public String placeBid() {
//        bid.setBidder(user);
//        bid.setItem(item);
//        bidService.addBid(bid);
//        return "bid_confirm.xhtml";
//    }
//}