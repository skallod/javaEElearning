package ru.rearitem.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by User on 31.12.2016.
 */
public class RIRequestListener implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent e) {
        //ServletContext context = e.getServletContext();
        System.out.println("request initialized");
        //ServletRequest req = e.getServletRequest();
        //synchronized (context) {
//            String name = "";
//            name = ((HttpServletRequest) req).getRequestURI();
            //context.log("Request for " + name);
        //}
    }
    public void requestDestroyed(ServletRequestEvent e) {
        // ?????????? ??? ??????????? ???????
        System.out.println("listn request destroyed");
    }
}
