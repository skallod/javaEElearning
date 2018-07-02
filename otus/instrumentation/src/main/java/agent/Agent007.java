package agent;

import java.lang.instrument.Instrumentation;

public class Agent007 {

    private static volatile Instrumentation instrumentation;

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Hello! I`m java agent");
        System.out.println("Agent Counter");
        instrumentation.addTransformer(new ClassTransformer());
        Agent007.instrumentation=instrumentation;
    }

    public static long getSize(Object obj){
        return instrumentation.getObjectSize(obj);
    }
}