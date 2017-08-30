package com.actionbazaar.web;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.actionbazaar.buslogic.BidManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "BidManagerServlet", urlPatterns = {"/restbid"})
public class BidManagerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(BidManagerServlet.class);

    @EJB
    private BidManager bidManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("Servlet BookServlet doGet begin");
        log.info("Servlet BookServlet doGet end");
    }

}