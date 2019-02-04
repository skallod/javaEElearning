package ru.galuzin.camel_servlet.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyAcceptorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.apache.activemq.artemis.core.server.Queue;
import org.apache.activemq.artemis.core.server.impl.AddressInfo;
import org.apache.activemq.artemis.core.settings.HierarchicalRepository;
import org.apache.activemq.artemis.core.settings.impl.AddressSettings;
import org.apache.activemq.artemis.jms.client.ActiveMQConnection;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.apache.activemq.artemis.jms.server.config.ConnectionFactoryConfiguration;
import org.apache.activemq.artemis.jms.server.config.JMSConfiguration;
import org.apache.activemq.artemis.jms.server.config.JMSQueueConfiguration;
import org.apache.activemq.artemis.jms.server.config.impl.ConnectionFactoryConfigurationImpl;
import org.apache.activemq.artemis.jms.server.config.impl.JMSConfigurationImpl;
import org.apache.activemq.artemis.jms.server.config.impl.JMSQueueConfigurationImpl;
import org.apache.activemq.artemis.jms.server.embedded.EmbeddedJMS;

import javax.jms.*;
import javax.naming.InitialContext;
import java.net.SocketTimeoutException;
import java.util.Date;

@Slf4j
public class ActiveMqExecutor {

    volatile InitialContext initialContext;

    public ActiveMqExecutor() {

    }

    public ConnectionFactory exec() throws Exception {
        ActiveMQServer server = ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
                .setPersistenceEnabled(true)
                //.setPersistDeliveryCountBeforeDelivery(true)
                .setJournalDirectory("activemq/persist/journal")
                .setPagingDirectory("activemq/paging")
                .setBindingsDirectory("activemq/binding")
                .setLargeMessagesDirectory("activemq/largemessage")
                .setSecurityEnabled(false)
                .addAcceptorConfiguration("invm", "vm://0"))
                /*.addAddressInfo(new AddressInfo())*/;
//        HierarchicalRepository<AddressSettings> addressSettingsRepository = server.getAddressSettingsRepository();
//        addressSettingsRepository.
        server.start();

        //InitialContext initialContext = null;
        // Step 2. Create an initial context to perform the JNDI lookup.
        initialContext = new InitialContext();

        // Step 3. Look-up the JMS queue
        ActiveMQQueue queue = (ActiveMQQueue) initialContext.lookup("queue/exampleQueue");

        // Step 4. Look-up the JMS connection factory
        ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        return cf;
    }

    public InitialContext getInitialContext() {
        return initialContext;
    }

    public void startProducer() {
        // Step 5. Send and receive a message using JMS API
        new Thread(() -> {
            try {
                ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
                ActiveMQQueue queue = (ActiveMQQueue) initialContext.lookup("queue/exampleQueue");
                try (Connection connection = cf.createConnection()) {
                    connection.start();

                    Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
                    MessageProducer producer = session.createProducer(queue);
                    //for(int i=0; i<1; i++){
                        TextMessage message = session.createTextMessage("Hello sent at " + new Date());
                        log.info("Sending message: " + message.getText());
                        //priority >=0 >9
                        producer.send(message,DeliveryMode.PERSISTENT,5,10_000L);
                        log.info("message was sent");
                    //}
                }
            } catch (Exception e) {
                log.error("producer ", e);
            }
        }).start();
    }

    public void startProducerTransacted() {
        // Step 5. Send and receive a message using JMS API
        new Thread(() -> {
            try {
                ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
                ActiveMQQueue queue = (ActiveMQQueue) initialContext.lookup("queue/exampleQueue");
                try (Connection connection = cf.createConnection()) {
                    connection.start();

                    Session session = connection.createSession(false,Session.SESSION_TRANSACTED );
                    MessageProducer producer = session.createProducer(queue);
                    //for(int i=0; i<1; i++){
                        TextMessage message = session.createTextMessage("Hello sent at " + new Date());
                        log.info("Sending message: " + message.getText());
                        //priority >=0 >9
                        producer.send(message,DeliveryMode.PERSISTENT,5,10_000L);
                        log.info("message was sent");
                    //}
                }
            } catch (Exception e) {
                log.error("producer ", e);
            }
        }).start();
    }

    public void startConsumer() {
        // Step 5. Send and receive a message using JMS API
        new Thread(() -> {
            try {
                ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
                ActiveMQQueue queue = (ActiveMQQueue) initialContext.lookup("queue/exampleQueue");
                try (Connection connection = cf.createConnection()) {
                    ActiveMQConnection consumerConnection = (ActiveMQConnection)connection;
                    //consumerConnection.setR
                    connection.start();

                    Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
                    MessageConsumer messageConsumer = session.createConsumer(queue);

                    while (true) {
                        log.debug("ready to receive");
                        TextMessage messageReceived = (TextMessage) messageConsumer.receive(1000);
                        if(messageReceived!=null) {
                            log.info("Received message:" + messageReceived.getText());
                            messageReceived.acknowledge();
                            log.info("acknoowledged");
                            Thread.sleep(1000);
                        }
                    }

                }
            } catch (Exception e) {
                log.error("consumer ", e);
            }
        }).start();
    }

    public void startConsumerNewApi() {
        // Step 5. Send and receive a message using JMS API
        new Thread(() -> {
            try {
                ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
                ActiveMQQueue queue = (ActiveMQQueue) initialContext.lookup("queue/exampleQueue");
                /*try (*/JMSContext context = cf.createContext(Session.CLIENT_ACKNOWLEDGE);// {

                    context.start();
                    JMSConsumer messageConsumer = context.createConsumer(queue);

                    //while (true) {
//                        log.debug("ready to receive");
                        messageConsumer.setMessageListener(new MessageListener() {
                            @Override
                            public void onMessage(Message message) {
                                TextMessage messageReceived = (TextMessage) message;
                                if (messageReceived != null) {
                                    try {
                                        log.info("Received message:" + messageReceived.getText());
                                        if(true){
                                            //context.rollback();
                                            throw new SocketTimeoutException("omit acknowledge");
                                        }
                                        Thread.sleep(1000);
                                        messageReceived.acknowledge();
                                        log.info("acknoowledged");
                                    }catch (Exception e){
                                        log.error("on message",e);
                                        //messageReceived.
                                        context.recover();
                                    }
                                }
                            }
                        });
                    //}

                //}
            } catch (Exception e) {
                log.error("consumer ", e);
            }
        }).start();
    }

    public void startConsumerTransacted() {
        // Step 5. Send and receive a message using JMS API
        new Thread(() -> {
            try {
                ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
                ActiveMQQueue queue = (ActiveMQQueue) initialContext.lookup("queue/exampleQueue");
                /*try (*/JMSContext context = cf.createContext(Session.SESSION_TRANSACTED);// {

                    context.start();
                    JMSConsumer messageConsumer = context.createConsumer(queue);

                    //while (true) {
//                        log.debug("ready to receive");
                        messageConsumer.setMessageListener(new MessageListener() {
                            @Override
                            public void onMessage(Message message) {
                                TextMessage messageReceived = (TextMessage) message;
                                if (messageReceived != null) {
                                    try {
                                        log.info("Received message:" + messageReceived.getText());
                                        if(true){
                                            //context.rollback();
                                            throw new SocketTimeoutException("omit acknowledge");
                                        }
                                        Thread.sleep(1000);
                                        context.commit();
                                        log.info("commit");
                                    }catch (Exception e){
                                        log.error("on message",e);
                                        //messageReceived.
                                        context.rollback();
                                    }
                                }
                            }
                        });
                    //}

                //}
            } catch (Exception e) {
                log.error("consumer ", e);
            }
        }).start();
    }

}
