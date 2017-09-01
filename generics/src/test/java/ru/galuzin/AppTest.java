package ru.galuzin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.galuzin.enum_test.GdsNameVo;
import ru.galuzin.generics.Maximum;
import ru.galuzin.generics.ValueHolder;

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
        if("".equals(null)){
            System.out.println("eq");
        }else{
            System.out.println("not eq");
        }
        int x=4;
        System.out.println("x="+Integer.toString(x));
        String ordType = "DESC";
        if(OrderType.valueOf(ordType)==OrderType.DESC){
            System.out.println("OK");
        }

        System.out.println(Maximum.class.getName());

        assertTrue( true );


    }

    public void test1 (){
        int sizeInBytes = 4913152;//379;
//transform in MB
        double sizeInMb = ((double)sizeInBytes) / (1024 * 1024);
        System.out.println("sizeInMb = " + sizeInMb);
        //DecimalFormat df = new DecimalFormat("#.####");
        double newSize = (double)Math.round(sizeInMb * 10d) / 10d;
        System.out.println("newSize = " + newSize);
        if(newSize<sizeInMb){
            newSize+=0.1;
        }
        System.out.println("newSize = " + newSize);
        //df.setRoundingMode(RoundingMode.CEILING);
//        for (Number n : Arrays.asList(12, 123.12345, 0.23, 0.1, 2341234.212431324)) {
//            Double d = n.doubleValue();
//            System.out.println(df.format(d));
//        }
    }
    
    public void test2 (){
        BigDecimal bigDecimal = new BigDecimal(3.31);
        System.out.println("bigDecimal = " + bigDecimal);
        Double db = new Double(bigDecimal.doubleValue());
        System.out.println("db = " + db);
        
    }

    public void test3(){
        Set<String> set1 = new HashSet<String>();
        System.out.println("set="+collectionToString(set1,",",false));
    }
    
    public void test4(){
        LinkedList<String> list1 = new LinkedList<String>();
        list1.add("temp1");
        list1.add("temp2");
        list1.add("temp3");
        ListIterator<String> iter = list1.listIterator(list1.size());
        while(iter.hasPrevious()){
            System.out.println("iter = " + iter.previous());
        }
//        for(Iterator iter = list1.descendingIterator(); iter.hasNext();){
//            System.out.println("str1 = " + str1);
//        }
    }

    public void test5(){
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("1", new Integer(1));
        map.put("2", 2);
        System.out.println("map1 = " + map.get("1"));
        System.out.println("map2 = " + map.get("2"));
        Object obj1 = map.get("1");
        Object obj2 = map.get("2");
        System.out.println("dsa");

    }

    public void test6(){
        List<GdsNameVo> list = new LinkedList<>();
        list.add(GdsNameVo.AEROEXPRESS);
        list.add(GdsNameVo.GALILEO);
        tempMethod(list);
    }

    private <T>void tempMethod(T... a){
        for(T b : a){
            System.out.println("b = " + b);
        }
    }


    enum OrderType{
        ASC,DESC
    }

    public static String collectionToString(final Iterable<?> coll,
                                            final String delim, final boolean unique) {
        StringBuilder result = new StringBuilder();
        Set<String> set = unique ? new HashSet<String>() : null;
        for (Object obj : coll) {
            if (obj == null) {
                continue;
            }
            String str = obj.toString();
            if ((str==null) || ((set != null) && set.contains(str))) {
                continue;
            }
            if (result.length() > 0) {
                result.append(delim);
            }
            result.append(str);
            if (set != null) {
                set.add(str);
            }
        }
        return result.toString();
    }

    public void testMathLog(){
        long time = System.currentTimeMillis();
        //System.out.println(""+Math.sqrt(4));
        double result= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.PI)))),2d)))),2d)),2d));
        for(int i=0; i<1000000;i++){
            result+= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.PI)))),2d)))),2d)),2d));
        }
        System.out.println(String.format("%d , %f",System.currentTimeMillis()-time,result));
    }

    public void test7(){
        BigDecimal bd = new BigDecimal(251);
        bd = bd.multiply(new BigDecimal(0.18d));
        System.out.println("bd = " + bd);
        if((true || true)&&false){
            System.out.println("fdsa");
        }else{
            System.out.println("cxzv");
        }
    }

    public void test8() throws IOException {
        String url =
                "http://195.151.27.110/u1c/NDS.aspx?ticket=4212442254014";
        try {
            HttpURLConnection conn =
                    (HttpURLConnection) new URL(url).openConnection();
            int respCode = conn.getResponseCode();

            if (respCode == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new FileNotFoundException();
            }

            String contentType = conn.getContentType();
            String contentDispositionHeader =
                    conn.getHeaderField("content-disposition");
            int contentLength = conn.getContentLength();
            conn.getResponseMessage();
            conn.getInputStream();
            System.out.println("conn = " + conn);
            /*if (contentType.startsWith("text/") || (contentLength == -1)) {
                throw new IOException("This is not a binary file.");
            }*/
            InputStream raw = conn.getInputStream();
        }finally {

        }

    }
    @org.junit.Test
    public void test9(){
        String number = "III-AP958-443";
        number = number.replace("-", "");
        System.out.println(number);
    }

    public void test10(){
        class A {
            void method1(){
                throw new RuntimeException("message1");
            }
        }
        A a1 = new A();
        try {
            a1.method1();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void test11(){
        boolean a=true;
        boolean b=true;
        boolean c=false;
        if(a && !(b && c) ){
            System.out.println("yes");
        }else{
            System.out.println("no");
        }
        long minutes = 419;
        long hours = minutes/60;
        long minutes2 = minutes%60;
        System.out.println("hours = " + hours);
        System.out.println("minutes2 = " + minutes2);
        TYPES.type1.name();
        String tp = "type3";
        TYPES temp = TYPES.valueOf(tp);
        System.out.println("temp = " + temp);
    }

    public void test12(){
        System.out.println(String.valueOf(new DecimalFormat("0.00").format(BigDecimal.valueOf(78.5399986624718))));

        A a1 = new A();
        a1.types=TYPES.type1;

        A a2 = new A();
        a2.types=TYPES.type2;

        A a3 = new A();
        a3.types=TYPES.type2;

        List<A> list = new LinkedList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        int s = (int)list.stream().filter(a -> a.types!=null && a.types!=TYPES.type1).count();
        System.out.println("s = " + s);

        String string = "h";
        switch (TYPES.valueOf(string)){
            default:
                System.out.println("ok");
                break;
        }

    }

    public void test13(){
        StringBuilder result = new StringBuilder();
        System.out.println("result = " + result);
        marketingInfolog.info("");

    }

    public void test14(){
        Map<A,A> map = new HashMap<>();
        A a1= new A();a1.name="Leo"; a1.lastname="Galuzin"; a1.types= TYPES.type1;
        A a2= new A();a2.name="Den"; a2.lastname="Zizin"; a2.types= TYPES.type2;
        map.put(a1, a2);
        A a3 = map.get(a1);
        System.out.println("a3.name = " + a3.name);
        a3.types = TYPES.valueOf("type1");
        ValueHolder<Boolean> vh = new ValueHolder<>();
        System.out.println("vh = " + vh.getValue());
    }

    private static enum TYPES{type1, type2}
    static class A {
        String name;
        String lastname;
        TYPES types;
    }

    protected final transient Log marketingInfolog =
            LogFactory.getLog(getClass().getName() + ".MARKETING");


}
