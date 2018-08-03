package ru.galuzin.admin.servlet_time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by User on 30.12.2016.
 */
@WebServlet(name="time", urlPatterns = "/admin/time")
public class TimeSessionServlet extends HttpServlet {
    volatile boolean flag = true;

    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    )throws ServletException{
        performTask(req, resp);
    }

    private void performTask(
        HttpServletRequest req,
        HttpServletResponse resp)throws ServletException{
        System.out.println("time session thread name "+Thread.currentThread().getName());
        //getServletContext()
        HttpSession session = null;
        session = req.getSession(false);
        try(PrintWriter out = resp.getWriter()){
            out.write("<p>time servlet</p> "+(session==null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
