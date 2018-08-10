package ru.rearitem.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ru.galuzin.db_service.DbService;
import ru.galuzin.model.Role;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional.ofNullable(req.getSession(false)).ifPresent(HttpSession::invalidate);
        Map<String, String[]> parameterMap = req.getParameterMap();
        Optional<String> user = Optional.ofNullable(parameterMap.get("user")).map(strings -> strings[0]);
        if(user.isPresent()){
            Optional<byte[]> password = Optional.ofNullable(parameterMap.get("password")).map(strings
                    -> strings[0].getBytes(StandardCharsets.UTF_8));
            if(password.isPresent()){
                DbService dbservice = (DbService) req.getServletContext().getAttribute("dbservice");
                try {
                    Optional<String> accountUid = dbservice.isAccountExist(user.get(), password.get());
                    if(accountUid.isPresent()){
                        List<Role> roles = dbservice.getRoles(accountUid.get());
                        if(!roles.isEmpty()){
                            HttpSession session = req.getSession(true);
                            session.setAttribute("role", roles.get(0));//todo Set<Roles>
                            resp.setStatus(200);
                            return;
                        }
                    }
                } catch (SQLException e) {
                    req.getServletContext().log("account check",e);
                }
            }
        }
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
