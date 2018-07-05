package ru.galuzin.servlet_time;

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
public class TimeSession {
    public static void go(
            HttpServletRequest req,
            HttpServletResponse resp,
            HttpSession session
    ) {
        try(PrintWriter out = resp.getWriter();){
            out.write("<br> time session Tread name: "+Thread.currentThread().getName());
            LongMethod.invokeAndLog(out, " time session ");
            if(session==null){
                out.write("session disabled");
            }else{
                out.write("<br> creation time "
                        +new Date(session.getCreationTime()));
                out.write("session alive !");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
