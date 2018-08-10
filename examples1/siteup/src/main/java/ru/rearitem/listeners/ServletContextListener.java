package ru.rearitem.listeners;

import javax.servlet.ServletContextEvent;

import ru.galuzin.db_service.DbService;
import ru.galuzin.db_service.DbServiceImpl;

public class ServletContextListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("context init");
        sce.getServletContext().setAttribute("dbservice", new DbServiceImpl());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DbService dbService = (DbService)sce.getServletContext().getAttribute("dbservice");
        dbService.shutdown();
    }
}
