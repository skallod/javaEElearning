package ru.galuzin.camel_servlet.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import ru.galuzin.camel_servlet.camel.processors.SimplerProcessor;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.camel.LoggingLevel.ERROR;

public class FirstRoute extends RouteBuilder {

    public static final String TRACE_ID = "TRACE_ID";

    @Override
    public void configure() throws Exception {
        //context scope
        errorHandler(deadLetterChannel("activemq:queue:deadLetter")
                //.useOriginalMessage()
                //.asyncDelayedRedelivery() //have not understand yet
                .maximumRedeliveries(0)
                .redeliveryDelay(10_000)
                // .logRetryStackTrace(true)
                .logStackTrace(true)
                .retryAttemptedLogLevel(ERROR));
        onException(IOException.class).maximumRedeliveries(3);
        onException(HttpOperationFailedException.class).onWhen(//EXCEPTION_CAUGHT
                //header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(504))
                exceptionMessage().contains("statusCode: 504"))//nginx reverse proxy timeout
                .maximumRedeliveries(2);
        //ExecutorService executor = Executors.newFixedThreadPool(10);

        from("jetty://http://localhost:8889/greeting")
//                from("jetty://http://localhost:8888/greeting")
                .convertBodyTo(String.class)
                .setProperty(TRACE_ID).constant(UUID.randomUUID().toString())
                .log("Received a request ${property.TRACE_ID} ${exchangeId}; ${id}; ${body}")

                //.process(simplerProcessor)
//                         use wiretap to continue processing the message
//                 in another thread and let this consumer continue
                .wireTap("direct:incoming")
                // and return an early reply to the waiting caller
                .log(ERROR, "http response OK ${property.TRACE_ID} ${exchangeId}; ${id};")
                .transform().constant("OK");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        from("direct:incoming").routeId("process")
//                    .multicast().parallelProcessing().executorService(executor)
                // convert the jetty stream to String so we can safely read it multiple times
                .convertBodyTo(String.class)
                .log(ERROR, "Incoming ${property.TRACE_ID} ${exchangeId}; ${id}; ${body}")
                .to("activemq:queue:exampleQueue")
                .log(ERROR, "aftere queue ${property.TRACE_ID} ${exchangeId}; ${id};");
        //.setBody(simple("Hello, world!"));
        SimplerProcessor simplerProcessor = new SimplerProcessor();
        from("activemq:queue:exampleQueue")
                .log(ERROR, "message processing ${property.TRACE_ID} ${exchangeId}; ${id};${body}")
                //.setHeader("from", constant("corteos"))
                //.multicast().parallelProcessing().executorService(executor)
                //.delay(10_000)
                .process(simplerProcessor)//.rollback("***Rollback");
                //.to("direct:update");
                //.process(simplerProcessor).onException(Exception.class).stop()//.rollback("***Rollback");
                .log(ERROR, "send via http ${property.TRACE_ID} ${exchangeId}; ${id}; ${body}")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET")) //netty4-http
                .to("jetty://http://localhost:55559/api/logout")
                .log(ERROR, "http was sent");
        ;//.onException(NettyHttpOperationFailedException.class).maximumRedeliveries(2);
//               from("direct:update")
//                       .log(LoggingLevel.ERROR,"from direct ${body}")
//                       .process(simplerProcessor)//.rollback("***Rollback");
//                       .log(LoggingLevel.ERROR,"send via http ${body}")
//                       .removeHeaders("*")        //netty4-http
//                       .to("jetty://http://localhost:55559/api/async")
//               ;
        from("activemq:queue:deadLetter")
                //.process(deadLetterProcessor)
                .log(ERROR,"dead letter processing ${property.TRACE_ID} ${exchangeId}; ${id};")
                .to("log:ru.galuzin.camel_servlet.camel?level=ERROR&showCaughtException=true&showStackTrace=true")//showAll,showCaughtException=true
                .delay(1000);//.rollback("***Rollback");
    }


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
}
