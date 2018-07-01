package ru.galuzin;

import ru.galuzin.my_arraylist.MyArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        List<String> list = new MyArrayList<String>();
        List<String> list2 = new MyArrayList<String>();

        Collections.addAll(list, "first", "second", "good");
        System.out.println("list2 = " + list2);

        Collections.copy(list2,list);

        Collections.sort(list2, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Objects.requireNonNull(o1);
                Objects.requireNonNull(o2);
                if(o1.isEmpty() || o2.isEmpty()){
                    throw new IllegalArgumentException("comporator empty string");
                }
                int i = o1.codePointAt(0);
                int j = o2.codePointAt(0);
                return (i>j)?1:(i==j)?0:-1;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}

class A{
    int b;
    String c;
}
