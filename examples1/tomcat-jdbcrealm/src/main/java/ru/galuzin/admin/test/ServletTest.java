package ru.galuzin.admin.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicLong;

@WebServlet(name="test", urlPatterns = "/admin/test")
public class ServletTest extends HttpServlet {
    AtomicLong longAdder = new AtomicLong();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try (Writer out = resp.getWriter()){
                //Thread.sleep(1_000);
                out.write("<p>hello world 5</p>");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
