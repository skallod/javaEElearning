package ru.rearitem.servlets;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "createAccount", urlPatterns = "/api/async", asyncSupported=true)
public class AsyncServletTest extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(AsyncServletTest.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext ctx = req.startAsync(req, resp);
        log.info("async start");
        new Thread(()->{
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ServletResponse response = ctx.getResponse();
            log.info("response class "+response.getClass().getName());
            ctx.dispatch();
        });
    }
}
