package ru.rearitem.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.db_service.DbService;
import ru.galuzin.model.Account;
import ru.galuzin.model.Error;
import ru.galuzin.model.Role;
import ru.rearitem.utils.Constants;
import ru.rearitem.utils.HashUtil;
import ru.rearitem.utils.ParamsValidator;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(name = "account", urlPatterns = "/api/account")
public class CreateAccountServlet extends HttpServlet implements Constants{

    private static final Logger log = LoggerFactory.getLogger(CreateAccountServlet.class);

    public static final String MESSAGE_ACCOUNT_ALREADY_EXISTS = "{\"message\":\"account already exists\"}";

    private DbService dbservice;

    @Override
    public void init(ServletConfig config) throws ServletException {
        dbservice = (DbService) config.getServletContext().getAttribute("dbservice");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("create account");
        try {
            List<Error> validate = ParamsValidator.validate(req, resp, "email", "password", "name");
            if(!validate.isEmpty()){
                return;
            }
            String user = req.getParameter("email");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            try {
                boolean exist = dbservice.isEmailExist(user);
                log.info("email exist "+exist);
                if (exist) {
                    try (PrintWriter wr = resp.getWriter()) {
                        wr.write(MESSAGE_ACCOUNT_ALREADY_EXISTS);
                        resp.setStatus(SC_BAD_REQUEST);
                        resp.setContentType(CONTENT_TYPE_APPLICATION_JSON);
                        return;
                    }
                } else {
                    Account account = new Account(
                            UUID.randomUUID().toString(),
                            user,
                            name,
                            HashUtil.hash(password.getBytes(StandardCharsets.UTF_8))
                    );
                    dbservice.saveAccountWithRole(account, Role.USER);
                    resp.setStatus(SC_OK);
                    return;
                }
            } catch (SQLException e) {
                log.error("account check", e);
            }
        } catch (Exception e) {
            log.error("create account", e);
        }
        resp.sendError(SC_BAD_REQUEST);
    }
}
