package ru.galuzin.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletTest extends HttpServlet {
    private static  final Logger log = LoggerFactory.getLogger(ServletTest.class);
    AtomicLong longAdder = new AtomicLong();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("test servlet start " + longAdder.incrementAndGet());
        //new Thread(()->{ todo этот сервлет так не умеет

            try (Writer out = resp.getWriter()){
                Thread.sleep(10_000);
                log.info("test servlet finish");
                out.write("<p>hello world</p>");
            } catch (Exception e) {
                log.error("test servlet error",e);
                e.printStackTrace();
            }

        //}).start();
    }
}
