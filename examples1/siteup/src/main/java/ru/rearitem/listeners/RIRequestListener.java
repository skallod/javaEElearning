package ru.rearitem.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by User on 31.12.2016.
 */
public class RIRequestListener implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent e) {
        ServletContext context = e.getServletContext();
        HttpSession session = ((HttpServletRequest)e.getServletRequest()).getSession(true);
        String uid = UUID.randomUUID().toString();
        session.setAttribute("_uid",uid);
        context.log("listener request initialized "+uid);
        //ServletRequest req = e.getServletRequest();
        //synchronized (context) {
//            String name = "";
//            name = ((HttpServletRequest) req).getRequestURI();
            //context.log("Request for " + name);
        //}
    }
    public void requestDestroyed(ServletRequestEvent e) {
//        HttpSession session = ((HttpServletRequest)e.getServletRequest()).getSession(true);
//        String uid = (String)session.getAttribute("_uid");
//        System.out.println("listener request destroyed " + uid);
    }
}
