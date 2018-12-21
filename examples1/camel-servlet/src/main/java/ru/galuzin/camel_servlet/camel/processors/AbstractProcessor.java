package ru.galuzin.camel_servlet.camel.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

@Slf4j
public abstract class AbstractProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
        try {
            //log.info("process message = " + message.getBody().toString()+" ; "+message.getMessageId()+);
            processExchange(exchange);
        }catch (Throwable e){
            log.error("",e);
            //message.setFault(true);//error handler ignore
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
            //throw e;
        }
    }

    abstract void processExchange(Exchange exchange) throws Exception;
}
