package ru.galuzin.servlet_login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import ru.galuzin.utils.LongMethod;

/**
 * Created by User on 30.12.2016.
 */
public class SessionLogic {

    ThreadLocal<Integer> internalCounter = new ThreadLocal<>();

    public static void printToBrowser(
            HttpServletResponse resp, HttpServletRequest req
    ) {

            HttpSession session = req.getSession(true);
        try (PrintWriter out = resp.getWriter();){

            StringBuffer url =  req.getRequestURL();
            session.setAttribute("URL", url);
            out.write("<html><body>");
            out.write("My session counter:");
            out.write(String.valueOf(
                    prepareSessionCounter(session)
            ));
            out.write("<br> Creation time"
                    +new Date(session.getCreationTime()));
            out.write("<br> Time of last access :"
                    +new Date(session.getLastAccessedTime()));
            out.write("<br> session ID : "
                    +session.getId());
            out.write("<br> Your URL: "+url);
            out.write("<br> session logic Tread name: "+Thread.currentThread().getName());
            LongMethod.invokeAndLog(out, " session logic ");
            int timeLive= 300;//60 sec
            session.setMaxInactiveInterval(timeLive);
            out.write("<br>Set max inactive interval :"+
            timeLive+"sec");

            out.write("</body></hmtl>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int prepareSessionCounter(HttpSession session){
        Integer counter  = (Integer)session.getAttribute("counter");
        if(counter==null){
            session.setAttribute("counter",1);
            return 1;
        }else {
            counter ++;
            session.setAttribute("counter", counter);
            return counter;
        }
    }
}
