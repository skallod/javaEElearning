package ru.galuzin;

import junit.framework.TestCase;
import ru.galuzin.model.NamedValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * Created by galuzin on 10.01.2017.
 */
public class StreamTest extends TestCase {
    public void test1() throws ParseException {
        ArrayList<NamedValue> arrayList = new ArrayList<>();
        arrayList.add(new NamedValue("name1","value1"));
        arrayList.add(new NamedValue("name2","value2"));
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String resDateStr = "2016-12-12 11:11";
        Date resDate = frmt.parse(resDateStr);
        Date newDate = null;
        Optional<NamedValue> optional = arrayList.stream().filter(namedValue ->
                "flight.timelimit".equals(namedValue.getName())).findFirst();
        if(optional.isPresent()){
            throw new RuntimeException();
        }else {
            newDate = resDate;
            System.out.println("frmt.format(newDate) = " + frmt.format(newDate));
        }
        arrayList.add(new NamedValue("flight.timelimit","2017-01-01 01:01"));

        optional = arrayList.stream().filter(namedValue ->
                "flight.timelimit".equals(namedValue.getName())).findFirst();
        if(optional.isPresent()){
            newDate = frmt.parse(optional.get().getValue());
            System.out.println("frmt.format(newDate) = " + frmt.format(newDate));
        }
    }
}
