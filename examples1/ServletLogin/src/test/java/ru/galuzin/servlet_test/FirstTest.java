package ru.galuzin.servlet_test;

import junit.framework.TestCase;

/**
 * Created by galuzin on 20.02.2017.
 */
public class FirstTest extends TestCase{
    ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public void test1 (){
        System.out.println("threadLocal.get() = " + threadLocal.get());
        threadLocal.get().intValue()++;
        System.out.println("threadLocal.get() = " + threadLocal.get());
    }
}
