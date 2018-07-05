package ru.galuzin.my_arraylist;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class MyArrayListTest{

    List<String> list = new MyArrayList<String>();

    @Before
    public void prepare(){
        list.clear();
    }

    @Test
    public void testOneElementRemove(){
        list.add("one");
        Iterator iterator = list.iterator();
        iterator.next();
        iterator.remove();
        System.out.println("list = " + list);
        assertThat(list.isEmpty(),is(true));
        assertThat(iterator.hasNext(),is(false));
        iterator.next();
    }

    @Test
    public void addAll() {
        Collections.addAll(list,"1","2","3","4","5");
        assertThat(list.size(),is(5));
        assertThat(list.get(0),is("1"));
        assertThat(list.get(4),is("5"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void lastElementRemove(){
        Collections.addAll(list,"1","2","3");
        System.out.println("list = " + list);
        Iterator iterator = list.iterator();
        while (iterator.hasNext())iterator.next();
        iterator.remove();
        System.out.println("list = " + list);
        assertThat(list.size(),is(2));
        assertThat(list.get(1),is("2"));
        Object o = list.get(2);
        System.out.println("o = " + o);
    }
    @Test
    public void listCopy(){
        Collections.addAll(list, "first", "second", "good" , "look","ended");
        List<String> list2 = new MyArrayList<String>();
        Collections.addAll(list2, "","","","","");
        Collections.copy(list2, list);
        assertThat(list2.size(),is(5));
        assertThat(list2.get(0),is("first"));
        assertThat(list2.get(4),is("ended"));
    }

    @Test
    public void listSort(){
        Collections.addAll(list, "first", "second", "good" , "look","ended");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Objects.requireNonNull(o1);
                Objects.requireNonNull(o2);
                if (o1.isEmpty() || o2.isEmpty()) {
                    throw new IllegalArgumentException("comporator empty string");
                }
                int i = o1.codePointAt(0);
                int j = o2.codePointAt(0);
                return (i > j) ? 1 : (i == j) ? 0 : -1;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        System.out.println("list = " + list);
        assertThat(list.get(0),is("ended"));
        assertThat(list.get(4),is("second"));
    }

    @Test
    public void forEachTest(){
        Collections.addAll(list, "first", "second", "good" , "look","ended");
        //iterator use
        for(String el : list){
            System.out.println("el = " + el);
        }
    }

    @Test
    public void equalsTest(){
        Collections.addAll(list, "first", "second", "good" , "look","ended");
        List<String> list2 = new MyArrayList<String>();
        Collections.addAll(list2, "","","","","");
        Collections.copy(list2, list);
        System.out.println("list2 = " + list2);
        assertThat(list2.equals(list),is(true));
        assertThat(list2.hashCode()==list.hashCode(), is(true));
        list2.set(4, "");
        assertThat(!list2.equals(list),is(true));
        assertThat(list2.hashCode()!=list.hashCode(), is(true));
    }

}