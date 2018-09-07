package ru.rearitem.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name="logout", urlPatterns = "/api/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LogoutServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional.ofNullable(req.getSession(false)).ifPresent(HttpSession::invalidate);
        log.info("invalidate");
        resp.setStatus(200);
    }

    /*void test(){
        try {
            Class<?> aClass = Class.forName("org.postgresql.Driver");
            log.info("class loaded");
            try {
                DriverManager.registerDriver((Driver) aClass.newInstance());
                try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbservice", "postgres"
                        , "postgres");
                    Statement statement = connection.createStatement();){
                    ResultSet execute = statement.executeQuery("select * from roles;");
                    while (execute.next()){
                        String role = execute.getString("role_name");
                        log.info("role "+role);
                    }
                    execute.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("test conn",e);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
