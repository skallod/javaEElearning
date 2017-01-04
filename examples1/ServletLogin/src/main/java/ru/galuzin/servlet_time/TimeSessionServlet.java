package ru.galuzin.servlet_time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by User on 30.12.2016.
 */
public class TimeSessionServlet extends HttpServlet {
    boolean flag = true;

    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    )throws ServletException{
        performTask(req, resp);
    }

    private void performTask(
        HttpServletRequest req,
        HttpServletResponse resp
    )throws ServletException{
        HttpSession session = null;
        if(flag){
            session = req.getSession();
            int timeLive = 10;
            session.setMaxInactiveInterval(timeLive);
            flag=false;
        }else{
            session = req.getSession(false);
        }
        TimeSession.go(req,resp,session);
    }
}
