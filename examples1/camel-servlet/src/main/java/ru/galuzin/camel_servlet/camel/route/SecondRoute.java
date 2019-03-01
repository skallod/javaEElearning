package ru.galuzin.camel_servlet.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import ru.galuzin.camel_servlet.camel.processors.SimplerProcessor;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.camel.LoggingLevel.*;

public class SecondRoute extends RouteBuilder {

    public static final String TRACE_ID = "TRACE_ID";
    public static final String UNDELIVERED = "activemq:queue:undelivered2";
    public static final String SECOND_QUEUE = "activemq:queue:secondQueue";
    public static final String MESSAGE_BODY = "MESSAGE_BODY";
    public static final String QUEUE_DEAD_LETTER = "activemq:queue:deadLetter2";

    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel(QUEUE_DEAD_LETTER)
                .maximumRedeliveries(0)
                .redeliveryDelay(10_000)
                .logExhaustedMessageBody(true)
                .retryAttemptedLogLevel(ERROR)
        );

        onException(SocketException.class, InterruptedIOException.class)
                .maximumRedeliveries(150)
                .redeliveryDelay(10_000)
                .handled(true)
                .process(exchange -> {
                    log.warn("sending to undelivered "+exchange.getIn().getHeader(TRACE_ID));
                    exchange.getIn().setBody(exchange.getProperty(MESSAGE_BODY));
                })
                //
                .removeHeaders("*",TRACE_ID)
                .to(UNDELIVERED)
        ;

        from("netty4-http://http://0.0.0.0:8889/solution").routeId("solution_incoming")
                .convertBodyTo(String.class)
                .process((exchange)->{
                    exchange.getIn().setHeader(TRACE_ID, UUID.randomUUID().toString());
                })
                .log("Received a request ${in.header.TRACE_ID}")
                .removeHeaders("*",TRACE_ID)
                .inOnly(SECOND_QUEUE)
                .log(INFO, "http response OK ${in.header.TRACE_ID}")
                .transform().constant("OK");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        SimplerProcessor simplerProcessor = new SimplerProcessor();

        from(SECOND_QUEUE).routeId("from_queue_to_solution")
                .multicast().parallelProcessing().executorService(executor)
                .process((exchange)->exchange.setProperty(TRACE_ID,exchange.getIn().getHeader(TRACE_ID)))
                .process(exchange->exchange.setProperty(MESSAGE_BODY,exchange.getIn().getBody()))
                .log(INFO, "message second processing ${in.header.TRACE_ID} ${exchangeId}; ${id};${body}")
                .process(simplerProcessor)//.rollback("***Rollback");
                .log(INFO, "send via http ${in.header.TRACE_ID} ${exchangeId}; ${id}; ${body}")
                .removeHeaders("*",TRACE_ID)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .inOut("netty4-http://http://localhost:58088/api/async?disconnect=true&keepAlive=false")
                .log(INFO, "http was sent ${property.TRACE_ID} ${in.body}");//todo out.body test

        from(QUEUE_DEAD_LETTER)
                .to("log:ru.galuzin.camel_servlet.camel?level=ERROR&showCaughtException=true&showStackTrace=true")//showAll,showCaughtException=true
                .delay(1000);

        from(UNDELIVERED).routeId("undelivered2").autoStartup(true)
                .log(WARN,"undelivered process ${in.header.TRACE_ID}")
                .removeHeaders("*",TRACE_ID)
                .inOnly(SECOND_QUEUE);//.rollback("***Rollback");
    }
}
