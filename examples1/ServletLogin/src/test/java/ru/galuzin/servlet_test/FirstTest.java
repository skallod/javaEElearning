package ru.galuzin.servlet_test;

import junit.framework.TestCase;

/**
 * Created by galuzin on 20.02.2017.
 */
public class FirstTest extends TestCase{
    ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return new Integer(1);
        }
    };
    public void test1 (){
        System.out.println("threadLocal.get() = " + threadLocal.get());
        threadLocal.set(threadLocal.get()+1);
        System.out.println("threadLocal.get() = " + threadLocal.get());
    }
}
