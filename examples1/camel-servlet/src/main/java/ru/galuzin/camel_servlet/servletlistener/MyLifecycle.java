//package ru.galuzin.camel_servlet.servletlistener;
//
//import org.apache.camel.component.servletlistener.CamelContextLifecycle;
//import org.apache.camel.component.servletlistener.ServletCamelContext;
////import org.apache.camel.impl.JndiRegistry;
//import org.apache.camel.impl.SimpleRegistry;
//
//public class MyLifecycle implements CamelContextLifecycle<SimpleRegistry> {
//
//    @Override
//    public void beforeStart(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
//        // enlist our bean(s) in the registry
//        registry.put("helloBean", new HelloBean());
////        registry.put("inputBean", new InputGenerator());
//    }
//
//    @Override
//    public void beforeStop(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
//        // noop
//    }
//
//    @Override
//    public void afterStop(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
//        // noop
//    }
//
//    @Override
//    public void beforeAddRoutes(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
//        // noop
//    }
//
//    @Override
//    public void afterAddRoutes(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
//        // noop
//    }
//
//    @Override
//    public void afterStart(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
//        // noop
//    }
//}