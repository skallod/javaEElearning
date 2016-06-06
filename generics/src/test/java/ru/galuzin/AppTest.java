package ru.galuzin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ru.galuzin.enum_test.GdsNameVo;
import ru.galuzin.generics.Maximum;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

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
}
