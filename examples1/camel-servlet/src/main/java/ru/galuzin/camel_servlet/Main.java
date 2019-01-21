package ru.galuzin.camel_servlet;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.camel_servlet.activemq.ActiveMqExecutor;
import ru.galuzin.camel_servlet.camel.CamelExecutor;
import ru.galuzin.camel_servlet.howtio.HawtioExecutor;

import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception{
        try{
            ActiveMqExecutor activeMqExecutor = new ActiveMqExecutor();
            ConnectionFactory cf = activeMqExecutor.exec();
            //activeMqExecutor.startProducer();
            //activeMqExecutor.startConsumer();
//            activeMqExecutor.startConsumerNewApi();
            //InitialContext initialContext = activeMqExecutor.getInitialContext();
            CamelExecutor camelExecutor = new CamelExecutor();
            camelExecutor.start(cf/*,initialContext*/);
//            HawtioExecutor hawtioExecutor = new HawtioExecutor();
//            hawtioExecutor.exec();
        }catch (Exception e){
            log.error("",e);
            throw e;
        }
    }
}
