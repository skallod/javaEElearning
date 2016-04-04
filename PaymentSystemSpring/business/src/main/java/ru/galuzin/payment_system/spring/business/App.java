package ru.galuzin.payment_system.spring.business;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.galuzin.payment_system.spring.business.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    final static Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        LOG.info( "Hello World!" );
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-database.xml","spring-context.xml"});
        AccountService accountService = context.getBean("accountService", AccountService.class);
        accountService.read();
    }
}