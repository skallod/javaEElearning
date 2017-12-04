package ru.rearitem.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by User on 05.01.2017.
 */
public class RIServlet extends HttpServlet {
    private StringBuffer locked= new StringBuffer();
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
        throws ServletException, IOException{
        performTask(req,res);
    }

    private void performTask(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("_uid");
        session.getServletContext().log("servlet req uid = " + uid);
        res.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = res.getWriter()){
//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            out.print(Math.round(Math.random() * 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private String createString() {
//        final String SYNCHRO = "SYNCHRONIZATION";
//        //synchronized (locked){
//            try{
//                for(int i=0; i < SYNCHRO.length();i ++){
//                    locked.append(SYNCHRO.charAt(i));
//                    System.out.println(Thread.currentThread().getName());
//                    Thread.sleep(1000);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String result = locked.toString();
//            locked.delete(0,SYNCHRO.length());
//            return result;
//        //}
//
//    }
}
