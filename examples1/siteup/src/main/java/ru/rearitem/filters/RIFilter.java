package ru.rearitem.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by User on 31.12.2016.
 */
public class RIFilter implements Filter {
    private FilterConfig filterConfig;
    public RIFilter(){
        super();
        System.out.println("filter constructed");
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        System.out.println("filter inited");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        String uid = (String)session.getAttribute("_uid");
        session.getServletContext().log("filter req uid = " + uid);
        //String val = "simple filter";
        //servletRequest.setAttribute("info",val);
    }

    @Override
    public void destroy() {
        System.out.println("filter destroy");
    }
}
