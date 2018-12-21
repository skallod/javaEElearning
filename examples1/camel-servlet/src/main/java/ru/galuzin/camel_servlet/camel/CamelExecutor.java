package ru.galuzin.camel_servlet.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.netty4.http.NettyHttpOperationFailedException;
import org.apache.camel.impl.DefaultCamelContext;
import ru.galuzin.camel_servlet.camel.processors.DeadLetterProcessor;
import ru.galuzin.camel_servlet.camel.processors.InputGenerator;
import ru.galuzin.camel_servlet.camel.processors.JmsConsumer;
import ru.galuzin.camel_servlet.camel.processors.SimplerProcessor;

import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CamelExecutor {

    public CamelExecutor(){

    }

    public void start(ConnectionFactory cf/*, InitialContext context*/) throws Exception{
        CamelContext camelContext = new DefaultCamelContext();
        //((DefaultCamelContext) camelContext).setJndiContext(context);
        camelContext.addComponent("activemq",
                JmsComponent.jmsComponentClientAcknowledge(cf));
                //ActiveMQComponent.("tcp://localhost:8161"));
        final InputGenerator inputGenerator = new InputGenerator();
        JmsConsumer jmsConsumer = new JmsConsumer();

//        camelContext.addRoutes(new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                from("timer:1s?fixedRate=true&period=1000")
//                        .process(inputGenerator)
//                        .transform(simple("${body.toUpperCase()}"))
//                        .to("activemq:queue:exampleQueue");
//                        //.to("log:edu.javacourse.camel?level=INFO");
//            }
//        });
        SimplerProcessor simplerProcessor = new SimplerProcessor();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jetty://http://localhost:8889/greeting")
//                from("jetty://http://localhost:8888/greeting")
                        .convertBodyTo(String.class)
                        .log("Received a request ${body}")

                        //.process(simplerProcessor)
//                         use wiretap to continue processing the message
//                 in another thread and let this consumer continue
                        .wireTap("direct:incoming")
                        // and return an early reply to the waiting caller
                        .log(LoggingLevel.ERROR,"http response OK")
                        .transform().constant("OK");

                ExecutorService executor = Executors.newFixedThreadPool(10);
                from("direct:incoming").routeId("process")
//                    .multicast().parallelProcessing().executorService(executor)
                    // convert the jetty stream to String so we can safely read it multiple times
                    .convertBodyTo(String.class)
                    .log(LoggingLevel.ERROR,"Incoming ${body}")
                    .to("activemq:queue:exampleQueue")
                    .log(LoggingLevel.ERROR,"aftere queue");
                    //.setBody(simple("Hello, world!"));
            }
        });



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
        camelContext.addRoutes(new RouteBuilder() {
           @Override
           public void configure() throws Exception {
               errorHandler(getDeadLetterHandler());
               ExecutorService executor = Executors.newFixedThreadPool(10);
               from("activemq:queue:exampleQueue")
                       .log(LoggingLevel.ERROR,"message processing ${body}")
               //.setHeader("from", constant("corteos"))
               //.multicast().parallelProcessing().executorService(executor)
                       //.delay(10_000)
               .process(simplerProcessor).onException(Exception.class).stop()//.rollback("***Rollback");
               .process(simplerProcessor).onException(Exception.class).stop()//.rollback("***Rollback");
               .log(LoggingLevel.ERROR,"send via http ${body}")
               .removeHeaders("*")        //netty4-http
               .to("jetty://http://localhost:58088/api/account2")
                       ;//.onException(NettyHttpOperationFailedException.class).maximumRedeliveries(2);
           }

            private DefaultErrorHandlerBuilder getDeadLetterHandler() {
                return deadLetterChannel("activemq:queue:deadLetter")
                //.useOriginalMessage()
                   //.asyncDelayedRedelivery() //have not understand yet
                .maximumRedeliveries(3)
                .redeliveryDelay(10_000)
//                .logRetryStackTrace(true)
                .logStackTrace(true)
                .retryAttemptedLogLevel(LoggingLevel.ERROR);
            }
        });
        DeadLetterProcessor deadLetterProcessor = new DeadLetterProcessor();
        camelContext.addRoutes(new RouteBuilder() {
           @Override
           public void configure() throws Exception {
               from("activemq:queue:deadLetter")
               //.process(deadLetterProcessor)
               .to("log:ru.galuzin.camel_servlet.camel?level=ERROR&showAll=true")//showCaughtException=true
               .delay(1000);//.rollback("***Rollback");
           }
       });

        /*//               DefaultErrorHandlerBuilder defaultErrorHandler = defaultErrorHandler()
//                       .maximumRedeliveries(1)
//                       .redeliveryDelay(100_000)
//                       .retryAttemptedLogLevel(LoggingLevel.WARN);
//               defaultErrorHandler.setDeadLetterUri("activemq:queue:dead");*/

        camelContext.start();
        log.error("registry "+camelContext.getRegistry());
    }
}
