package ru.galuzin.gradle_test;

public class Main {
    public static void main(String[] args) {
        example1();
    }
    public static void example1 () {
        LombokExample ex1 = new LombokExample(10,"test",false);
        System.out.println(ex1.getSentence());
    }
}
