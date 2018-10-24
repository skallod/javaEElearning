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
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.model.Role;
import ru.rearitem.servlets.CreateAccountServlet;

public class AuthFilter implements Filter {//todo rename AuthFilter

    private static final Logger log = LoggerFactory.getLogger(CreateAccountServlet.class);

    private Role role;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String roleStr = filterConfig.getInitParameter("role");
        this.role = Role.valueOf(roleStr);
        log.info("filter init "+role.name());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("filter auth " + role.name());
        Optional<HttpSession> session = Optional.ofNullable(((HttpServletRequest) request).getSession(false));
        Set<Role> roleSet = session.map(s -> (Set<Role>) s.getAttribute("roles")).orElse(Collections.emptySet());
        if(roleSet.isEmpty()){
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else if(!roleSet.contains(role)){
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }else{
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
    }
}
