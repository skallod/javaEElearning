package ru.rearitem.servlets;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "asyncTest", urlPatterns = "/api/async", asyncSupported=true)
public class AsyncServletTest extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(AsyncServletTest.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext ctx = req.startAsync(req, resp);
        log.info("async start");
        ctx.start(()->{
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PrintWriter pw = null;
            try {
                pw = ctx.getResponse().getWriter();
                pw.write("async finish");
            }catch (Exception e){
                log.error("ctx print fail",e);
            }finally {
                if(pw!=null) {
                    pw.close();
                }
                ctx.complete();
                //ctx.dispatch();
            }
            //log.info("response class "+response.getClass().getName());
        });
    }
}
