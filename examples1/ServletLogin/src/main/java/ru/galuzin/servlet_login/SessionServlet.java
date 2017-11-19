package ru.galuzin.servlet_login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by User on 30.12.2016.
 */
public class SessionServlet extends HttpServlet {
    public SessionServlet(){
        super();
        System.out.println("servlet created");
    }
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException{
        performTask(req,resp);
    }
    private void performTask(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException{
        SessionLogic.printToBrowser(resp,req);
    }
}
