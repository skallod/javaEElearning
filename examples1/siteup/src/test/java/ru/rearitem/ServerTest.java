package ru.rearitem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletInfo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.db_service.DataSourceTest;
import ru.galuzin.db_service.DbServiceImpl;
import ru.rearitem.servlets.CreateAccountServlet;

public class ServerTest {
    private static final Logger log = LoggerFactory.getLogger(ServerTest.class);
    static Undertow server;
    static DbServiceImpl dbService;
    volatile static boolean stop;
    @BeforeClass
    public static void beforeClass() throws Exception{
        DataSourceTest dataSourceTest = new DataSourceTest();
        dbService = new DbServiceImpl(dataSourceTest);
        try(Connection conn = dataSourceTest.getConnection()){
           log.info("get conn "+conn);
        }
        //if(false) {
            DeploymentInfo myApp =
                    Servlets.deployment()
                            .setClassLoader(ServerTest.class.getClassLoader())
                            //new DeploymentInfo()
                            //.setClassLoader(CreateAccountServlet.class.getClassLoader())
                            .setContextPath("/")
                            .setDeploymentName("test.war")
                            .addServletContextAttribute("dbservice", dbService)
                            .addServlets(
                                    new ServletInfo("createAccountSrvlt", CreateAccountServlet.class)
                                    //.addInitParam("message", "Hello World")
                                .addMapping("/api/account")
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
//    @Test
//    public void test1(){
//        Scanner in = new Scanner(System.in);
//        String next = in.nextLine();
//        log.info("scanner in "+next);
////        try {
////            Thread.sleep(60_000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//    }
    @AfterClass
    public static void afterClass(){
        server.stop();
        dbService.shutdown();
    }
}
