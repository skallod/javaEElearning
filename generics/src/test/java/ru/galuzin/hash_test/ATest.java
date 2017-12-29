package ru.galuzin.hash_test;

import java.util.HashSet;

import org.junit.Test;

public class ATest {
    @Test
    public void test() throws Exception {
        System.out.println("\n\n");
        System.out.println("First step");
        HashSet<A> hts = new HashSet<>();
        A ht1 = new A("str1", 1);
        System.out.println("ht1 = " + ht1+" ; "+ht1.a+" ; "+ht1.b);
        hts.add(ht1);
        System.out.println("hts = " + hts);
        System.out.println("hts.contains ht1 = " + hts.contains(ht1));
        System.out.println("hts.size " + hts.size());
        System.out.println("\n======================================================\n");
        System.out.println("Second step");
        ht1.a = "change str1";
        System.out.println("ht1 = " + ht1+" ; "+ht1.a+" ; "+ht1.b);
        System.out.println("hts = " + hts);
        System.out.println("hts.contains ht1 = " + hts.contains(ht1));
        System.out.println("hts.size " + hts.size());
        System.out.println("\n======================================================\n");
        System.out.println("Third step");
        hts.add(ht1);
        System.out.println("hts = " + hts);
        System.out.println("hts.contains ht1 = " + hts.contains(ht1));
        System.out.println("hts.size " + hts.size());
    }
}
