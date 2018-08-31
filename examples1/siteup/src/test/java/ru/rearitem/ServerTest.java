package ru.rearitem;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ServletInfo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.db_service.DataSourceTest;
import ru.galuzin.db_service.DbServiceImpl;
import ru.rearitem.filters.AdminFilter;
import ru.rearitem.servlets.AdminLkServlet;
import ru.rearitem.servlets.LoginServlet;
import ru.rearitem.servlets.CreateAccountServlet;
import ru.rearitem.servlets.UserLkServlet;

public class ServerTest {
    private static final Logger log = LoggerFactory.getLogger(ServerTest.class);
    static Undertow server;
    static DbServiceImpl dbService;
    volatile static boolean stop;
    @BeforeClass
    public static void beforeClass() throws Exception{
        DataSourceTest dataSourceTest = new DataSourceTest();
        dbService = new DbServiceImpl(dataSourceTest);
        //if(false) {
        DeploymentInfo myApp =
            Servlets.deployment()
            .setClassLoader(ServerTest.class.getClassLoader())
            .setContextPath("/")
            .setDeploymentName("test.war")
            .addServletContextAttribute("dbservice", dbService)
            .addFilters(
                    new FilterInfo("userFilter", AdminFilter.class).addInitParam("role","USER"),
                    new FilterInfo("adminFilter", AdminFilter.class).addInitParam("role","ADMIN")
            )
            .addFilterUrlMapping("userFilter","/api/user/*", DispatcherType.REQUEST)
            .addFilterUrlMapping("adminFilter","/api/admin/*", DispatcherType.REQUEST)
            .addServlets(
                new ServletInfo("login", LoginServlet.class).addMapping("/api/login"),
                new ServletInfo("logout", LoginServlet.class).addMapping("/api/logout"),
                new ServletInfo("createAccount", CreateAccountServlet.class).addMapping("/api/account"),
                new ServletInfo("userLK", UserLkServlet.class).addMapping("/api/user/lk"),
                new ServletInfo("adminLK", AdminLkServlet.class).addMapping("/api/admin/lk")
                //new ServletInfo("h2", org.h2.server.web.WebServlet.class).addMapping("/api/h2")
            );
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(myApp);
        manager.deploy();
        PathHandler path = Handlers.path(Handlers.redirect(myApp.getContextPath()))
                .addPrefixPath(myApp.getContextPath(), manager.start());

        server = Undertow.builder()
                .addHttpListener(58088, "localhost")
                .setHandler(path)
                .build();
        server.start();
        //}
    }

    public static void main(String[] args) throws Exception {
        try {
            beforeClass();
            Thread mainThread = Thread.currentThread();
            new Thread(()->{
                try {
                    ServerSocket serverSocket = new ServerSocket(58089);
                    Socket accept = serverSocket.accept();
                    log.info("accepted");
                    accept.close();
                    serverSocket.close();
                    stop=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            do{
                Thread.sleep(1000);
            }while (!stop);
            log.info("main stop");
        }finally {
            afterClass();
        }
    }

    @AfterClass
    public static void afterClass(){
        server.stop();
        dbService.shutdown();
    }
}
