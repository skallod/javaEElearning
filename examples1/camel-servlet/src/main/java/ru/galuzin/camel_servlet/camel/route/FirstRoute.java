package ru.galuzin.camel_servlet.camel.route;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.apache.camel.builder.ExpressionClause;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty4.http.NettyHttpOperationFailedException;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.model.OnExceptionDefinition;
import ru.galuzin.camel_servlet.camel.processors.SimplerProcessor;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.camel.LoggingLevel.*;

public class FirstRoute extends RouteBuilder {

    public static final String TRACE_ID = "TRACE_ID";
    public static final String UNDELIVERED = "activemq:queue:undelivered";
    public static final String EXAMPLE_QUEUE = "activemq:queue:exampleQueue";
    public static final String MESSAGE_BODY = "MESSAGE_BODY";
    public static final String QUEUE_DEAD_LETTER = "activemq:queue:deadLetter";

    @Override
    public void configure() throws Exception {
        //context scope
        errorHandler(deadLetterChannel(QUEUE_DEAD_LETTER)
                //.useOriginalMessage()
                //.asyncDelayedRedelivery() //have not understand yet
                .maximumRedeliveries(0)
                .redeliveryDelay(10_000)
                //.logStackTrace(true)
                .logExhaustedMessageBody(true)
                .retryAttemptedLogLevel(ERROR)
        );
                // .logRetryStackTrace(true)
                //.log("Redelivery ${property.TRACE_ID} ${exchangeId}")
                //.logStackTrace(true)

        commonErrorHandler(onException(SocketException.class, SocketTimeoutException.class));
        commonErrorHandler(onException(IOException.class).onWhen(
                exceptionMessage().contains("хост принудительно разорвал существующее подключение")));


//                //.maximumRedeliveries(3);
//        onException(HttpOperationFailedException.class).onWhen(//EXCEPTION_CAUGHT
//                //header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(504))
//                exceptionMessage().contains("statusCode: 504"))//nginx reverse proxy gateway timeout
//                //exceptionMessage().contains("statusCode: 502"))//nginx reverse proxy bad gateway
//                .maximumRedeliveries(2);
//        onException(HttpOperationFailedException.class).onWhen(
//                exceptionMessage().contains("statusCode: 502"))//nginx reverse proxy gateway timeout
//                .maximumRedeliveries(2);
        from(/*jetty netty4-http*/"netty4-http://http://0.0.0.0:8889/greeting").routeId("http_incoming")
//                from("jetty://http://localhost:8888/greeting")
                .convertBodyTo(String.class)
                .process((exchange)->{
                    exchange.getIn().setHeader(TRACE_ID,UUID.randomUUID().toString());
                })
                //.setProperty(TRACE_ID).simple(UUID.randomUUID().toString())
                .log("Received a request ${in.header.TRACE_ID}")


                //.process(simplerProcessor)
//                         use wiretap to continue processing the message
//                 in another thread and let this consumer continue
                .removeHeaders("*",TRACE_ID)
                .inOnly(EXAMPLE_QUEUE)
                //.wireTap("direct:incoming")
                // and return an early reply to the waiting caller
                .log(ERROR, "http response OK ${in.header.TRACE_ID}")
                .transform().constant("OK");

        //ExecutorService executor = Executors.newFixedThreadPool(10);
//        from("direct:incoming").routeId("process")
////                    .multicast().parallelProcessing().executorService(executor)
//                // convert the jetty stream to String so we can safely read it multiple times
//                .convertBodyTo(String.class)
//                .log(ERROR, "Incoming ${property.TRACE_ID} ${exchangeId}; ${id}; ${body}")
//                //.process((exchange)->exchange.getIn().setHeader(TRACE_ID,exchange.getProperty(TRACE_ID)))
//                .to(EXAMPLE_QUEUE)
//                .log(ERROR, "aftere queue ${property.TRACE_ID} ${exchangeId}; ${id};");
        //.setBody(simple("Hello, world!"));
        SimplerProcessor simplerProcessor = new SimplerProcessor();
       // Endpoint endpoint = new Endpoint();

        from(EXAMPLE_QUEUE).routeId("from_queue_to_server")//todo processed serial add consumer
            .process((exchange)->exchange.setProperty(TRACE_ID,exchange.getIn().getHeader(TRACE_ID)))
            .process(exchange->exchange.setProperty(MESSAGE_BODY,exchange.getIn().getBody()))
                .log(ERROR, "message processing ${in.header.TRACE_ID} ${exchangeId}; ${id};${body}")
                //.setHeader("from", constant("corteos"))
                //.multicast().parallelProcessing().executorService(executor)
                //.delay(5_000)
                .process(simplerProcessor)//.rollback("***Rollback");
                //.to("direct:update");
                //.process(simplerProcessor).onException(Exception.class).stop()//.rollback("***Rollback");
                .log(ERROR, "send via http ${in.header.TRACE_ID} ${exchangeId}; ${id}; ${body}")
                .removeHeaders("*",TRACE_ID)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                //.doTry()/*58088*/
//                .inOut("netty4-http://http://localhost:55559/api/async?disconnect=true&keepAlive=false")//?disconnect=true&keepAlive=false")//NettyHttpOperationFailedException/statusCode: 504
                .delay(20_000)
                .inOut("netty4-http://http://localhost:58088/api/async")
                .log(INFO,"after http")
                .delay(20_000)
                .log(ERROR, "http was sent ${property.TRACE_ID} ${in.body}");

        ;//.onException(NettyHttpOperationFailedException.class).maximumRedeliveries(2);
//               from("direct:update")
//                       .log(LoggingLevel.ERROR,"from direct ${body}")
//                       .process(simplerProcessor)//.rollback("***Rollback");
//                       .log(LoggingLevel.ERROR,"send via http ${body}")
//                       .removeHeaders("*")        //netty4-http
//                       .to("jetty://http://localhost:55559/api/async")
//               ;
        from(QUEUE_DEAD_LETTER)
                //.process(deadLetterProcessor)
//                .log(ERROR,"dead letter processing ${property.TRACE_ID} ${exchangeId}; ${id};")
//                .process((exchange)->{
//                    exchange.getIn().setHeader(TRACE_ID,exchange.getProperty(TRACE_ID));
//                })
                .to("log:ru.galuzin.camel_servlet.camel?level=ERROR&showCaughtException=true&showStackTrace=true")//showAll,showCaughtException=true
                .delay(1000);

        from(UNDELIVERED).routeId("undelivered").autoStartup(true)
                //.process((exchange)->exchange.setProperty(TRACE_ID,exchange.getIn().getHeader(TRACE_ID)))
                .log(WARN,"undelivered process ${in.header.TRACE_ID}")
//                .process((exchange)->{
//                    exchange.getIn().setHeader(TRACE_ID,exchange.getProperty(TRACE_ID));
//                })
                //.delay(1_000)
                //.to("log:ru.galuzin.camel_servlet.camel?level=WARN");
                .removeHeaders("*",TRACE_ID)
                .inOnly(EXAMPLE_QUEUE);//.rollback("***Rollback");
    }

    void commonErrorHandler(OnExceptionDefinition def){
        //.handled(true)//excepction не прокинется на producer..
        //.continued(true)
                def.maximumRedeliveries(150)
                .redeliveryDelay(10_000)
                //.handled(true).process(exchange ->{
//                    exchange.removeProperty("CamelExceptionCaught");
//                    exchange.setException(null);
                //} )
                .process(exchange -> {
                    log.warn("sending to undelivered "+exchange.getIn().getHeader(TRACE_ID));
                    exchange.getIn().setBody(exchange.getProperty(MESSAGE_BODY));
                    //exchange.getIn().setHeader(TRACE_ID,exchange.getProperty(TRACE_ID));
                })
                //
                .removeHeaders("*",TRACE_ID)
                .to(UNDELIVERED)
        ;
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
