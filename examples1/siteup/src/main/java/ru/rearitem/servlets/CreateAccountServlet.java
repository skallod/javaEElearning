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
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import ru.galuzin.db_service.DbService;
import ru.galuzin.domain.Account;
import ru.galuzin.domain.Role;
import ru.rearitem.utils.Constants;
import ru.rearitem.utils.HashUtil;
import ru.rearitem.utils.ParamsValidator;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(name = "createAccount", urlPatterns = "/api/account")
@Slf4j
public class CreateAccountServlet extends HttpServlet implements Constants{

    public static final String MESSAGE_ACCOUNT_ALREADY_EXISTS = "{\"message\":\"account already exists\"}";

    private DbService dbservice;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbservice = (DbService) config.getServletContext().getAttribute("dbservice");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("create account");
        try {
            if(!ParamsValidator.validate(req, resp, "email", "password", "name").isEmpty()){
                return;
            }
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            boolean exist = dbservice.isEmailExist(email);
            log.info("email exist "+exist);
            if (exist) {
                try (PrintWriter wr = resp.getWriter()) {
                    wr.write(MESSAGE_ACCOUNT_ALREADY_EXISTS);
                    resp.setStatus(SC_BAD_REQUEST);
                    resp.setContentType(APPLICATION_JSON);
                    return;
                }
            } else {
                Account account = new Account(
                        email,
                        name,
                        HashUtil.hash(password.getBytes(StandardCharsets.UTF_8))
                );
                dbservice.saveAccountWithRole(account, Role.USER);
                //resp.addCookie(new Cookie("test","test"));
                resp.setStatus(SC_OK);
                return;
            }
        } catch (Exception e) {
            log.error("create account", e);
        }
        resp.sendError(SC_BAD_REQUEST);
    }
}
