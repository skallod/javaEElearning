package ru.galuzin;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

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

    public void testSomething(){
        Boolean issued = null;
        if (Boolean.TRUE.equals(issued)) {
            System.out.println("dasfas");
        }else{
            System.out.println("czxv");
        }
        //UUID.
    }

    public void testLoadStr() throws IOException {
        InputStream fis = new FileInputStream("c:\\temp\\roomOffer.txt");
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        StringBuilder stringBuilder= new StringBuilder();
        String line = null;
        while((line =br.readLine())!=null){
            stringBuilder.append(line);
        }
        br.close();
        System.out.println(stringBuilder.toString());
    }

    public void testWeakMap(){
        WeakHashMap<String,String> map = new WeakHashMap<String,String>();
        map.put("KEY","VALUE");
        tempMethod(map);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tempMethod(map);
    }

    private void tempMethod(Map<String,String> map){
        String str = map.get("KEY");
        float temp = 3.31f;
        System.out.println(String.valueOf(temp));
    }
    public void test4(){
        float temp = 3.00f;
        System.out.println(String.valueOf(temp));
    }
    public void test5(){
        Optional<TestType> temp = Enums.getIfPresent(TestType.class, "TEST1");
        if(temp.isPresent()){
            System.out.println("ok");
        }else{
            System.out.println("no");
        }
         //TestType.valueOf("good");
        //System.out.println(String.valueOf(temp));
    }
    enum TestType{TEST1, TEST2}
    public void test6(){
        String[] uids = new String[]{"543","42","765"};
        StringBuilder sb= new StringBuilder();
        for(String uid : uids){
            sb =(sb.length()==0)?sb.append(uid):sb.append(',').append(uid);
        }
        System.out.println("res="+sb.toString());
        uids = sb.toString().split(",");
        for(String uid : uids){
            System.out.println("uid="+uid);
        }
    }
    public void test7(){
        List<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        if(l1.contains("b")){
            System.out.println("ok");
        }
    }
    public void test8(){
        String str1 = "66520ed4-88fb-4ce9-aa08-bc5744a4e2a8|5cb76f9a-fba8-4d01-987c-bfbc310f5645";
        String str2 = "5cb76f9a-fba8-4d01-987c-bfbc310f5645";
        double k = 500;
        if(str1.contains(str2)){
            System.out.println("true");
        }
    }
}
