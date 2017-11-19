package ru.galuzin;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

/**
 * Created by galuzin on 20.06.2017.
 */
public class SerializationTest {
    static class A implements Serializable{
        static final long serialVersionUID = 42L;

        long count;

        Date date;

        Type type;

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        enum Type{BOOKING,REFUND;}
    }
    @Test
    public void test1()throws Exception{
        A a = new A();
        a.setCount(3);
        a.setType(A.Type.REFUND);
        ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream("c:\\temp\\a.ser"));
        ous.writeObject(a);
    }
    @Test
    public void test2()throws Exception{
        A a;
        ObjectInputStream ous = new ObjectInputStream(new FileInputStream("c:\\temp\\a.ser"));
        a = (A)ous.readObject();
        System.out.println("a.getType() = " + a.getType());
        System.out.println("a.date = " + a.date);
    }
    @Test
    public void test3()throws Exception{
        BigDecimal chisl = BigDecimal.valueOf(1.6);
        BigDecimal znam = BigDecimal.valueOf(9.2);
        BigDecimal res = chisl.divide(znam, 2, RoundingMode.HALF_UP);
        System.out.println("res = " + res);
        boolean[] booleans = new boolean[3]/*{false,false,false}*/;
        System.out.println("booleans = "+booleans[0]);
        //Arrays.asList(booleans).forEach(el->""+Boolean.valueOf(el));
    }
    enum En{ELEMENT}
    @Test
    public void test11112() throws IOException {
        System.out.println(En.ELEMENT.name());
        String str = "" + BigDecimal.valueOf((double)23*100/233).setScale(2,RoundingMode.HALF_UP)+ "%";
        System.out.println("str = " + str);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.close();
    }
    @Test
    public void test2334252()throws Exception{
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("zoneId = " + zoneId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Greenwich"));
        System.out.println("simpleDateFormat.getTimeZone() = " + simpleDateFormat.getTimeZone());
        Date parse = simpleDateFormat.parse("2017-01-25 21:00");
        System.out.println("parse = " + simpleDateFormat.format(parse));
        Calendar cldr = Calendar.getInstance();
        cldr.setTime(parse);
        ZonedDateTime d = ZonedDateTime.of(cldr.get(Calendar.YEAR), cldr.get(Calendar.MONTH)+1
                , cldr.get(Calendar.DAY_OF_MONTH), cldr.get(Calendar.HOUR_OF_DAY), cldr.get(Calendar.MINUTE), 0, 0, ZoneId.of("Greenwich"));
        System.out.println("d = " + d);
        ZonedDateTime zonedDateTime = d.toInstant().atZone(ZoneId.of("Europe/Moscow"));
        System.out.println("zonedDateTime = " + zonedDateTime);
//        ZonedDateTime d = ZonedDateTime.ofInstant(parse.toInstant(),
//                ZoneId.of("Greenwich"));
//        ZonedDateTime departure = ZonedDateTime.of(2014, 5, 10, 15, 5, 0, 0, ZoneId.of("America/Los_Angeles"));
//        ZonedDateTime arrival = departure.plusMinutes(650).toInstant().atZone(ZoneId.of("Europe/Berlin"));
//        System.out.println(arrival);
    }
    @Test
    public void test785(){
        ArrayList<String> objects = new ArrayList<>();
        objects.add("one");
        objects.add("two");
        System.out.println("objects = " + null);
    }
    @Test
    public void test0807(){
        try{
            throw new TimeoutException();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("catch");
            return;
        }finally {
            System.out.println("finally");
        }
    }@Test
    public void test0808(){
        LinkedList<String> strings = new LinkedList<>();
        strings.add("one");
        System.out.println("strings = " + ((Object)strings));
        strings.clear();
        System.out.println("strings = " + strings);
    }
}
