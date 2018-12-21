//package ru.galuzin.camel_servlet.camel.processors;
//
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.component.jms.JmsMessage;
//import org.apache.camel.impl.DefaultMessage;
//
//import java.util.Date;
//
//public class JmsGenerator  implements Processor {
//    @Override
//    public void process(Exchange exchange) throws Exception {
//
//        JmsMessage message = new JmsMessage();
//        message.setBody("current time: " + new Date());
//
//        exchange.setOut(message);
//
//    }
//}
