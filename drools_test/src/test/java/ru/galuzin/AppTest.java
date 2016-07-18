package ru.galuzin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
        JSONArray travelDocs = new JSONArray();
        System.out.println("test length "+travelDocs.length());

        System.out.println(java.io.Serializable.class.getName());

        assertTrue( true );
    }

    public void testSerial(){
        ru.galuzin.drools_test.Test test = new ru.galuzin.drools_test.Test();
        test.setDescription("kljl;j");
        ByteArrayOutputStream strm = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(strm);
            oos.writeObject(test);
            oos.flush();
            oos.close();
            byte[] bytes = strm.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
