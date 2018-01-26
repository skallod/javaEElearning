package ru.galuzin.hash_test;

import java.util.HashSet;

import org.junit.Test;

public class BTest {
    @Test
    public void test(){
        HashSet<B> set = new HashSet<>();
        for(int i=0;i<1_000_000; i++){
            set.add(new B("Test",1));//UUID.randomUUID().toString(), new Random().nextInt()));
        }
        System.out.println("set.size() = " + set.size());
    }
}
