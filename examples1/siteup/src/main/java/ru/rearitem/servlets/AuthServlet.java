package ru.rearitem.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.db_service.DbService;
import ru.galuzin.model.Role;
import ru.rearitem.utils.TextUtil;

public class AuthServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional.ofNullable(req.getSession(false)).ifPresent(HttpSession::invalidate);
        Map<String, String[]> parameterMap = req.getParameterMap();
        Optional<String> user = Optional.ofNullable(parameterMap.get("email")).map(strings -> strings[0]);
        if (!user.isPresent() || TextUtil.isEmpty(user.get())) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Optional<String> password = Optional.ofNullable(parameterMap.get("password")).map(strings
                -> strings[0]);
        if (!password.isPresent() || TextUtil.isEmpty(password.get())) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        DbService dbservice = (DbService) req.getServletContext().getAttribute("dbservice");
        try {
            Optional<String> accountUid = dbservice.isAccountExist(user.get(), password.get()
                    .getBytes(StandardCharsets.UTF_8));
            if (accountUid.isPresent()) {
                Set<Role> roles = dbservice.getRoles(accountUid.get());
                log.info("roles " + roles);
                if (!roles.isEmpty()) {
                    HttpSession session = req.getSession(true);
                    session.setAttribute("roles", roles);
                    resp.setStatus(200);
                    return;
                }
            }
        } catch (SQLException e) {
            log.error("account check", e);
        }
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
