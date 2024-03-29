package ru.galuzin.camel_servlet.camel.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

@Slf4j(topic = "first-route")
public class SimplerProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
        log.info("simple message = " + message + " ; "+exchange.getProperty("TRACE_ID"));
        exchange.getIn().setBody("{\"new\":\""+exchange.getProperty("TRACE_ID")+"\"}");
        //log.info(""+body);
//      work good message acknowleged exchange.setProperty(Exchange.ROUTE_STOP, true);
//        log.warn("Route stop"+ " ; "+exchange.getProperty("TRACE_ID"));
    }
}
