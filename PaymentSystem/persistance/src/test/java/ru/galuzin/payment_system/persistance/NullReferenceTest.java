package ru.galuzin.payment_system.persistance;

/**
 * Created by LEONID on 06.03.2016.
 */
public class NullReferenceTest {
    public static String hello ="Hello, world";
    //.....
    public static void main(String[] args) {
        NullReferenceTest nullReferenceTest = null;
        System.out.println(nullReferenceTest.hello);
    }
}