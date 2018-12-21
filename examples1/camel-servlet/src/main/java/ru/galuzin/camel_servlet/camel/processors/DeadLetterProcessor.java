package ru.galuzin.camel_servlet.camel.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;

@Slf4j
public class DeadLetterProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
        log.info("start dead letter "+exchange.getExchangeId()
                +" ; "+ message.getMessageId()+" ; "+message.getClass()+" ; "+message.getBody());
//        try {
//            ActiveMQTextMessage m = (ActiveMQTextMessage) ((JmsMessage) exchange.getIn()).getJmsMessage();
//            //int attempts = m.getIntProperty("remain-attempts");
//            //String from = m.getStringProperty("From");
//            log.error("dead letter message = " + m.getText() + " ; "/* + from*/);
//        }catch (Throwable e){
//            log.error("",e);
//            message.setFault(true);
//            //exchange.setOut(message);
//        }
    }
}
