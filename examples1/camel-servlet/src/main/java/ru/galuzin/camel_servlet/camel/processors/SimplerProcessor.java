package ru.galuzin.camel_servlet.camel.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

@Slf4j
public class SimplerProcessor extends AbstractProcessor{
    @Override
    void processExchange(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
        log.info("simple message = " + message);
        //throw new IllegalArgumentException("***");
    }
}
