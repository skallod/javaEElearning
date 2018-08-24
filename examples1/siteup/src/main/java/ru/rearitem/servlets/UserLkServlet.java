package ru.rearitem.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import ru.rearitem.utils.Constants;

@WebServlet(name = "account", urlPatterns = "/api/user/lk")
public class UserLkServlet  extends HttpServlet implements Constants {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
    }
}
