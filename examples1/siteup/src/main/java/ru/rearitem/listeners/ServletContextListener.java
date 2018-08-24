package ru.rearitem.listeners;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.db_service.DataSourceImpl;
import ru.galuzin.db_service.DbService;
import ru.galuzin.db_service.DbServiceImpl;

public class ServletContextListener implements javax.servlet.ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("context initializing");
        sce.getServletContext().setAttribute("dbservice", new DbServiceImpl(new DataSourceImpl()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("context destroying");
        DbService dbService = (DbService)sce.getServletContext().getAttribute("dbservice");
        dbService.shutdown();
    }

}
