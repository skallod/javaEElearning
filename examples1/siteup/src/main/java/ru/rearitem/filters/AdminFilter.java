package ru.rearitem.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import ru.galuzin.model.Role;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        HttpServletResponse httpresponse = (HttpServletResponse)response;
        Role role = (Role)session.getAttribute("role");
        if(role!=Role.ADMIN){
            httpresponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }else{
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
    }
}
