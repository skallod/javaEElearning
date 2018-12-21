package ru.galuzin.camel_servlet.camel.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQBytesMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;
import org.apache.camel.converter.stream.InputStreamCache;
import org.apache.camel.impl.DefaultMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class JmsConsumer  extends AbstractProcessor {

    @Override
    void processExchange(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
//        String result = new BufferedReader(new InputStreamReader((InputStreamCache)message.getBody()))
//                .lines().collect(Collectors.joining("\n"));
        log.info("message content "+message.getBody());
        //throw new IllegalArgumentException("***");
        //((JmsMessage)exchange.getIn()).getJmsMessage().acknowledge();
        //((JmsMessage)exchange.getIn()).getJmsSession().rollback();
        //((JmsMessage)exchange.getIn()).getJmsSession().close();

    }
}