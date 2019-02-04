package ru.galuzin.camel_servlet.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.ReplyToType;
import org.apache.camel.component.netty4.http.NettyHttpOperationFailedException;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.impl.DefaultCamelContext;
import ru.galuzin.camel_servlet.camel.processors.DeadLetterProcessor;
import ru.galuzin.camel_servlet.camel.processors.InputGenerator;
import ru.galuzin.camel_servlet.camel.processors.JmsConsumer;
import ru.galuzin.camel_servlet.camel.processors.SimplerProcessor;
import ru.galuzin.camel_servlet.camel.route.FirstRoute;

import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CamelExecutor {

    public CamelExecutor(){

    }

    public void start(ConnectionFactory cf/*, InitialContext context*/) throws Exception{
        CamelContext camelContext = new DefaultCamelContext();
        //((DefaultCamelContext) camelContext).setJndiContext(context);
        JmsComponent jmsComponent = JmsComponent.jmsComponentClientAcknowledge(cf);
        //jmsComponent.setAsyncConsumer(true);//client acknnowledge work immediatly after consume
        camelContext.addComponent("activemq", jmsComponent);
                //ActiveMQComponent.("tcp://localhost:8161"));
        final InputGenerator inputGenerator = new InputGenerator();
//        camelContext.addRoutes(new RouteBuilder() {
//           @Override
//           public void configure() throws Exception {
//               from("timer:1000s?fixedRate=true&period=1000000")
////               from("direct:start")
//               .process(inputGenerator)
//                       .log( "sending to queue")
//               .to("activemq:queue:exampleQueue")
//               .log("message sent ${body}");
//           }
//       });
        SimplerProcessor simplerProcessor = new SimplerProcessor();
        camelContext.addRoutes(new FirstRoute());

        //todo error handler root scope

        camelContext.start();
        log.error("registry "+camelContext.getRegistry());
    }
}
