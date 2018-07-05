package ru.galuzin.test_filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by User on 31.12.2016.
 */
public class MyFilter implements Filter {
    private FilterConfig filterConfig;
    public MyFilter(){
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
        System.out.println("filter enter");
        String val = "simple filter";
        servletRequest.setAttribute("info",val);
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("info="+val);
        System.out.println("filter finish");
    }

    @Override
    public void destroy() {
        System.out.println("filter destroy");
    }
}
