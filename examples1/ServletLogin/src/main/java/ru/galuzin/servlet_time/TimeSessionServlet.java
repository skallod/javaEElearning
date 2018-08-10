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
    volatile boolean flag = true;

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
        System.out.println("time session thread name "+Thread.currentThread().getName());
        //getServletContext()
        HttpSession session = null;
        if(flag){
            session = req.getSession();
            int timeLive = 100;
            session.setMaxInactiveInterval(timeLive);
            flag=false;
        }else{
            session = req.getSession(false);
        }
        System.out.println("session = " + session);
        TimeSession.go(req,resp,session);
    }
}
