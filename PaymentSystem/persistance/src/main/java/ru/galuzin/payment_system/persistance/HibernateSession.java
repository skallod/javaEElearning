package ru.galuzin.payment_system.persistance;

import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;

/**
 * Created by User on 29.01.2016.
 */
public class HibernateSession {
    private static SessionFactory sf = null;
    //private static ServiceRegistry serviceRegistry = null;
    static {
//        try{
        //hibernate 3 init
        {
            Configuration configuration = new Configuration();
            sf = configuration.configure().buildSessionFactory();
        }
        //hibernate 4
//        {
//            Configuration configuration = new Configuration();
//            configuration.configure();
//            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            sf = configuration.buildSessionFactory(serviceRegistry);
//        }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }

    public static void quit(){
        if(sf!=null){
            sf.close();
        }
        //hibernate 4
        //if(serviceRegistry!=null){
            //StandardServiceRegistryBuilder.destroy(serviceRegistry);
        //}
    }
}
