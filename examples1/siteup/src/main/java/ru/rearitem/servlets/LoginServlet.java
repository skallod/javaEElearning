package ru.rearitem.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.db_service.DbService;
import ru.galuzin.model.Role;
import ru.rearitem.utils.Constants;
import ru.rearitem.utils.HashUtil;
import ru.rearitem.utils.ParamsValidator;

@WebServlet(name="login", urlPatterns = "/api/login")
public class LoginServlet extends HttpServlet implements Constants{
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional.ofNullable(req.getSession(false)).ifPresent(HttpSession::invalidate);
        if(!ParamsValidator.validate(req, resp, "email", "password").isEmpty()){
            return;
        }
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        DbService dbservice = (DbService) req.getServletContext().getAttribute("dbservice");
        try {
            Optional<String> accountUid = dbservice.isAccountExist(email
                    , HashUtil.hash(password.getBytes(StandardCharsets.UTF_8)));
            if (accountUid.isPresent()) {
                Set<Role> roles = dbservice.getRoles(accountUid.get());
                if (!roles.isEmpty()) {
                    HttpSession session = req.getSession(true);
                    log.info("session id "+session.getId());
                    session.setAttribute("roles", roles);
                    resp.setStatus(200);
                    return;
                }
            }
        } catch (Exception e) {
            log.error("account check", e);
        }
        resp.setContentType(APPLICATION_JSON);
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
