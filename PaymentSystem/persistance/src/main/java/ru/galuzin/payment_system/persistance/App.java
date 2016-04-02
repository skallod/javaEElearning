package ru.galuzin.payment_system.persistance;

import ru.galuzin.payment_system.common_types.Account;
import ru.galuzin.payment_system.persistance.dao.BasicDAO;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );
        while (true) {
            System.out.println("iteration");
            BasicDAO basicDAO = new BasicDAO();
            Account account = (Account) basicDAO.getEntity(Account.class, 1L);
            Double money = account.getMoney();
            money = money * 1.1;
            account.setMoney(money);
            basicDAO.updateEntity(account);
            Thread.sleep(5000);
        }
    }
}
