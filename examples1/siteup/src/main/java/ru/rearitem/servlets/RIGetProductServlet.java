package ru.rearitem.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RIGetProductServlet extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(RIGetProductServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("get product start");
        try {
            //URL resource = getClass().getResource("product.json");
            //log.info("resource = " + resource);
            //final byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(getJson());
            resp.getWriter().flush();
            resp.getWriter().close();
        }catch (Exception e){
            log.error("",e);
        }
    }

    private String getJson(){
        return "{\"product\":{\"code\": \"HatProduct\",\"price\": 11,\"description\": \"волшебный товар\",\"exist\": \"true\"}}";
    }
}
