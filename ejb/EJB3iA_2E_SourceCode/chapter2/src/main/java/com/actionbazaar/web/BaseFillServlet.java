package com.actionbazaar.web;


import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import com.actionbazaar.buslogic.ItemService;
import com.actionbazaar.buslogic.UserService;
import com.actionbazaar.persistence.Bidder;
import com.actionbazaar.persistence.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "BaseFillServlet", urlPatterns = {"/basefill"})
public class BaseFillServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(BaseFillServlet.class);
    @EJB
    ItemService itemService;
    @EJB
    UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        log.info("servlet item service "+ itemService);
        Item item = new Item();
        item.setItemName("Стул");
        item.setCreatedDate(new Date());
        item.setBidStartDate(new Date());
        item.setInitialPrice(33d);
        itemService.createItem(item);
        Bidder user= new Bidder("leo","gal", 10L);
        userService.createUser(user);
    }
}
