package ru.galuzin.servlet;

import ru.galuzin.long_time.ValMain;

import java.io.*;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class HelloWorld extends HttpServlet {

    private String message;

    public void init() throws ServletException
    {
        // Do required initialization
        message = "WTF";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        // Set response content type
        response.setContentType("text/html");

        String[] args = new String[0];
        ValMain.main(args);

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h3>" + message + "</h3>");
    }

    public void destroy()
    {
        // do nothing.
    }
}