package ru.galuzin.payment_system.persistance;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ru.galuzin.payment_system.common_types.Account;
import ru.galuzin.payment_system.persistance.dao.AccountDAO;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Account account = new Account();
        account.setMoney(10.0);
        new AccountDAO().addAccount(account);
//        assertTrue( true );
    }

}
