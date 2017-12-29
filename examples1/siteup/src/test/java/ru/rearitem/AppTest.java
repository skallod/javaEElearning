package ru.rearitem;

import java.util.Random;
import java.util.UUID;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
        String s = UUID.randomUUID().toString();
        System.out.println("s = " + s);
        assertTrue( true );
        int b=0;
        do {
            int i = new Random().nextInt();
            if(i<0)i=-i;
            b = i % 10;
            System.out.println("b = " + b);
        }while (b<5);
    }
}
