package ru.galuzin.payment_system.business;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.galuzin.payment_system.business.service.AccountService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-database.xml","spring-context.xml"});
        AccountService accountService = context.getBean("accountService", AccountService.class);
        accountService.read();
    }
}