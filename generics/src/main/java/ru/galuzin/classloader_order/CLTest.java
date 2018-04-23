package ru.galuzin.classloader_order;

public class CLTest {

    static {
        s = "second";
    }
    static String s = "first";

    {
        s2 = "locb";
    }
    String s2 = "loc";

}
