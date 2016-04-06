package ru.galuzin.payment_system.spring.business;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import ru.galuzin.payment_system.spring.business.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.payment_system.spring.common_types.*;
import ru.galuzin.payment_system.spring.persistance.dao.BasicDAO;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    final static Logger LOG = LoggerFactory.getLogger(App.class);
    static ApplicationContext context;

    public static void main( String[] args )
    {
        LOG.info( "Hello World!" );
        context = new ClassPathXmlApplicationContext(new String[]{"spring-database.xml"});
//        AccountService accountService = context.getBean("accountService", AccountService.class);
//        accountService.read();
        loadStructure();
    }

    public static void loadStructure(){
//        Account account = new Account();
//        account.setMoney(10D);
//        Account account2 = new Account();
//        account2.setMoney(10D);

        Person person = new Person();
        person.setAddress("Spb");
        person.setLastName("Repa");
        person.setName("Leo");

//        Person person2 = new Person();
//        person2.setAddress("Zeon");
//        person2.setLastName("Loop");
//        person2.setName("Den");

//        account.setPerson(person);
//        account2.setPerson(person2);

//        Terminal terminal = new Terminal();
//        terminal.setAddress("Generala belogo 14");

        //====== операция "перевод денег" ======
//        Operation operation = new Operation();
//        operation.setMoney(5D);
//        operation.setDate(new Date());
//        operation.setOperationType(OperationType.MONEY_ORDER.toString());
//        operation.setSrcAccount(account);
//        operation.setDestAccount(account);
//        operation.setTerminal(terminal);

        //====== операция "положить деньги на счет" ====
//        Operation operation2 = new Operation();
//        operation2.setMoney(15.5D);
//        operation2.setDate(new Date());
//        operation2.setOperationType(OperationType.PUT_MONEY.toString());
//        operation2.setSrcAccount(null);
//        operation2.setDestAccount(account);
//        operation2.setTerminal(terminal);
//
//        //====== операция "снять деньги со счета" ====
//        Operation operation3 = new Operation();
//        operation3.setMoney(9.99D);
//        operation3.setDate(new Date());
//        operation3.setOperationType(OperationType.GET_MONEY.toString());
//        operation3.setSrcAccount(account);
//        operation3.setDestAccount(null);
//        operation3.setTerminal(terminal);

        BasicDAO basicDAO = context.getBean("basicDAO",BasicDAO.class);

        basicDAO.addEntity(person);
//        basicDAO.addEntity(person2);
//        basicDAO.addEntity(account);
//        basicDAO.addEntity(account2);
//        basicDAO.addEntity(terminal);
//        basicDAO.addEntity(operation);
//        basicDAO.addEntity(operation2);
//        basicDAO.addEntity(operation3);
    }
}