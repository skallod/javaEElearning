package ru.galuzin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Map;

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
        Double a= Double.valueOf(0);
        Map<String,Double> nearMap = new HashMap<String,Double>();
        nearMap.put("sdaf", a);
        for(Map.Entry<String,Double> entry: nearMap.entrySet()){
            //entry.getKey()
        }

        assertTrue( true );
    }
}
