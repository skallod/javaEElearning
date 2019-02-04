//package ru.galuzin.camel_servlet;
//
//import io.undertow.Handlers;
//import io.undertow.Undertow;
//import io.undertow.server.handlers.PathHandler;
//import io.undertow.servlet.Servlets;
//import io.undertow.servlet.api.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletException;
//import javax.servlet.SessionTrackingMode;
//import java.util.HashSet;
//
//public class Server {
//    private static final Logger log = LoggerFactory.getLogger(Server.class);
//    private static Undertow server;
//    void start() throws ServletException {
////        ServletSessionConfig servletSessionConfig = new ServletSessionConfig();
////        servletSessionConfig.setSessionTrackingModes(
////                new HashSet<SessionTrackingMode>(){{add(SessionTrackingMode.COOKIE);}});
//        DeploymentInfo myApp =
//            Servlets.deployment()
//                    .setClassLoader(Server.class.getClassLoader())
//                    .setContextPath("/")
//                    .setDeploymentName("camel.war")
//                    .addInitParameter("routeBuilder-MyRoute","classpath:camel-config.xml")
//                    .addInitParameter("CamelContextLifecycle"
//                            ,"ru.galuzin.camel_servlet.servletlistener.MyLifecycle")
//                    //the listener that kick-starts Camel
////                    .addListener(new ListenerInfo(org.apache.camel.component.servletlistener.SimpleCamelServletContextListener.class))
//                    .addListener(new ListenerInfo(org.apache.camel.component.servletlistener.JndiCamelServletContextListener.class))
//                    //.setServletSessionConfig(servletSessionConfig)
////                    .addServletContextAttribute("dbservice", dbService)
////                    .addFilters(
////                            new FilterInfo("CamelServlet", org.apache.camel.component.servlet.CamelHttpTransportServlet.class)
////                    )
////                    .addFilterUrlMapping("userFilter","/api/user/*", DispatcherType.REQUEST)
//                    .addServlets(
//                            new ServletInfo("CamelServlet", org.apache.camel.component.servlet.CamelHttpTransportServlet.class)
//                                    .setLoadOnStartup(1).addMapping("/camel/*")
//                    )
//        ;
//        DeploymentManager manager = Servlets.defaultContainer().addDeployment(myApp);
//        manager.deploy();
//        PathHandler path = Handlers.path(Handlers.redirect(myApp.getContextPath()))
//                .addPrefixPath(myApp.getContextPath(), manager.start());
//
//        server = Undertow.builder()
//            .addHttpListener(59088, "localhost")
//            .setHandler(path)
//            .build();
//        server.start();
//    }
//}
