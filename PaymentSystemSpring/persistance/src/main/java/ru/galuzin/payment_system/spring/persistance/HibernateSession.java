//package ru.galuzin.payment_system.spring.persistance;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//
///**
// * Created by User on 29.01.2016.
// */
//public class HibernateSession {
//    private static SessionFactory sf = null;
//    private static ServiceRegistry serviceRegistry = null;
//    static {
//            Configuration configuration = new Configuration();
//            configuration.configure();
//            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            sf = configuration.buildSessionFactory(serviceRegistry);
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sf;
//    }
//
//    public static void quit(){
//        if(sf!=null){
//            sf.close();
//        }
//        if(serviceRegistry!=null){
//            StandardServiceRegistryBuilder.destroy(serviceRegistry);
//        }
//    }
//}
