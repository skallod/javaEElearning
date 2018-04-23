package ru.galuzin.classloader_order;

public class CLMain {
    public static void main(String[] args) throws Exception{
        Class.forName("ru.galuzin.classloader_order.CLTest");
        System.out.println(CLTest.s);
        CLTest clTest = new CLTest();
        System.out.println("clTest.s2 = " + clTest.s2);
    }
}
