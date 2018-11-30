package ru.galuzin.my_junit.tested;

import ru.galuzin.my_junit.junit.After;
import ru.galuzin.my_junit.junit.Before;
import ru.galuzin.my_junit.junit.Test;

public class A {
    @Before
    public void before(){
        System.out.println("before");
    }
    @Test
    public void test1(){
        System.out.println("test 1");
    }
    @Test
    public void test2(){
        System.out.println("test 2");
    }
    @After
    public void after(){
        System.out.println("after");
    }
}
