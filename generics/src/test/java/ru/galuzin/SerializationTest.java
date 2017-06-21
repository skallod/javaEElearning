package ru.galuzin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

/**
 * Created by galuzin on 20.06.2017.
 */
public class SerializationTest {
    static class A implements Serializable{
        static final long serialVersionUID = 42L;

        long count;

        Date date;

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }
    //@Test
    public void test1()throws Exception{
        A a = new A();
        a.setCount(3);
        ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream("c:\\temp\\a.ser"));
        ous.writeObject(a);
    }
    @Test
    public void test2()throws Exception{
        A a;
        ObjectInputStream ous = new ObjectInputStream(new FileInputStream("c:\\temp\\a.ser"));
        a = (A)ous.readObject();
        System.out.println("a.date = " + a.date);
    }
}
