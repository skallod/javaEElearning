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
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.model.Role;
import ru.rearitem.servlets.CreateAccountServlet;

public class AdminFilter implements Filter {

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
        log.info("filter auth "+role.name());
        HttpSession session = ((HttpServletRequest)request).getSession();
        HttpServletResponse httpresponse = (HttpServletResponse)response;
        Optional<Set<Role>> roleSetOpt = Optional
                .ofNullable((Set<Role>)session.getAttribute("roles"));
        if(!roleSetOpt.isPresent() || !roleSetOpt.get().contains(role/*Role.ADMIN*/)){
            httpresponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }else{
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
    }
}
