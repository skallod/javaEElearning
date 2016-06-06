package ru.galuzin;

import org.junit.Test;
import ru.galuzin.time_format.TemplateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by galuzin on 02.06.2016.
 */
public class TemplateUtilTest {
    @Test
    public void test(){
        Date timelimit = new Date();
        Map<Locale,String> map = new HashMap<>();
        Locale l1 = new Locale("ru","RU");
        Locale l2 = new Locale("ru");

        map.put(new Locale("ru","RU"),"TEST1");
        map.put(new Locale("ru"),"TEST");
        String str =        TemplateUtil.format(timelimit, "dd MMMMMMM, HH:mm", Locale.getDefault()/*new Locale("ru")*/);
        System.out.println("str = " + str);
        System.out.println("Locale.getDefault() = " + Locale.getDefault());
        if(Locale.getDefault().equals(new Locale("ru"))){
            System.out.println("fas");
        }
        System.out.println("map.get(Locale.getDefault()) = " + map.get(Locale.getDefault()));

        if(l1.equals(l2)){
            System.out.println("EQUALS");
        }else{
            System.out.println("NOT EQ");
        }
    }
}
