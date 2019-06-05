package ru.rearitem;

import javax.servlet.DispatcherType;
import javax.servlet.SessionTrackingMode;
import java.util.HashSet;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ServletInfo;
import io.undertow.servlet.api.ServletSessionConfig;
import io.undertow.util.Headers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.db_service.DataSourceTest;
import ru.galuzin.db_service.DbServiceImpl;
import ru.rearitem.filters.AuthFilter;
import ru.rearitem.httpclient.HttpClientAdapter;
import ru.rearitem.servlets.AdminLkServlet;
import ru.rearitem.servlets.AsyncServletTest;
import ru.rearitem.servlets.LoginServlet;
import ru.rearitem.servlets.CreateAccountServlet;
import ru.rearitem.servlets.LogoutServlet;
import ru.rearitem.servlets.UserLkServlet;

public class ServerTest {
    private static final Logger log = LoggerFactory.getLogger(ServerTest.class);
    private static Undertow server;
    private static DbServiceImpl dbService;
    private volatile static boolean stop;
    protected static HttpClientAdapter client;
    @BeforeClass
    public static void beforeClass() throws Exception{
        DataSourceTest dataSourceTest = new DataSourceTest();
        dbService = new DbServiceImpl(dataSourceTest);
        ServletSessionConfig servletSessionConfig = new ServletSessionConfig();
        servletSessionConfig.setSessionTrackingModes(
                new HashSet<SessionTrackingMode>(){{add(SessionTrackingMode.COOKIE);}});
        DeploymentInfo myApp =
            Servlets.deployment()
            .setClassLoader(ServerTest.class.getClassLoader())
            .setContextPath("/")
            .setDeploymentName("test.war")
            .setServletSessionConfig(servletSessionConfig)
            .addServletContextAttribute("dbservice", dbService)
            .addFilters(
                    new FilterInfo("userFilter", AuthFilter.class).addInitParam("role","USER"),
                    new FilterInfo("adminFilter", AuthFilter.class).addInitParam("role","ADMIN")
            )
            .addFilterUrlMapping("userFilter","/api/user/*", DispatcherType.REQUEST)
            .addFilterUrlMapping("adminFilter","/api/admin/*", DispatcherType.REQUEST)
            .addServlets(
                new ServletInfo("login", LoginServlet.class).addMapping("/api/login"),
                new ServletInfo("logout", LogoutServlet.class).addMapping("/api/logout"),
                new ServletInfo("createAccount", CreateAccountServlet.class).addMapping("/api/account"),
                new ServletInfo("userLK", UserLkServlet.class).addMapping("/api/user/lk"),
                new ServletInfo("adminLK", AdminLkServlet.class).addMapping("/api/admin/lk"),
                new ServletInfo("asyncTest", AsyncServletTest.class).addMapping("/api/async").setAsyncSupported(true)
            );
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(myApp);
        manager.deploy();
        PathHandler path = Handlers.path(Handlers.redirect(myApp.getContextPath()))
                .addPrefixPath(myApp.getContextPath(), manager.start())
                .addExactPath("/api2/hello", new HttpHandler() {
            @Override
            public void handleRequest(final HttpServerExchange exchange) throws Exception {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                log.info("hello handler");
                exchange.getResponseSender().send("Hello World");
            }
        });

        server = Undertow.builder()
                .addHttpListener(58088, "localhost")
                .setHandler(path)
                .build();
        server.start();
        client = new HttpClientAdapter();
    }

    public static void main(String[] args) throws Exception {
        try {
            beforeClass();
            log.info("server started");
            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                afterClass();
            }));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void afterClass(){
        log.info("after class");
        server.stop();
        dbService.shutdown();
        client.dispose();
    }
}
