package ru.galuzin.servlet_synch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by User on 05.01.2017.
 */
public class ServletSynchronization extends HttpServlet {
    private StringBuffer locked= new StringBuffer();
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
        throws ServletException, IOException{
        performTask(req,res);
    }

    private void performTask(HttpServletRequest req, HttpServletResponse res) {
        try(Writer out = res.getWriter()){
            out.write( "<HTML><HEAD>" + "<TITLE>SynchronizationDemo</TITLE>" + "</HEAD><BODY>");
            out.write(createString());
            out.write("</BODY></HTML>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createString() {
        final String SYNCHRO = "SYNCHRONIZATION";
        synchronized (locked){
            try{
                for(int i=0; i < SYNCHRO.length();i ++){
                    locked.append(SYNCHRO.charAt(i));
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = locked.toString();
            locked.delete(0,SYNCHRO.length());
            return result;
        }

    }
}
