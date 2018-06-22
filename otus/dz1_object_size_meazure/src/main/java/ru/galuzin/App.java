package ru.galuzin;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String... args) throws Exception {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        int size = 20_000_000;

        System.out.println("Starting the loop");
        Runtime runtime = Runtime.getRuntime();

        gcInvoke();
        long usedmem = runtime.totalMemory() - runtime.freeMemory();
        Object[] array = new Object[size];
        gcInvoke();
        long usedmemAfterArrInit = runtime.totalMemory() - runtime.freeMemory();
        long refsize = delta(usedmemAfterArrInit, usedmem, size);
        System.out.println("refsize = " + refsize);
        ArrayList<Supplier> suppliers = new ArrayList<Supplier>() {{
            add(Object::new);
            add(String::new);//string with pool
            add(() -> new String(new char[0]));
            add(MyClass::new);
        }};

        for (Supplier supllier :
                suppliers) {
            for (int i = 0; i < size; i++) {
                array[i] = supllier.get();
            }
            gcInvoke();
            long usedmemAfterFillArr = runtime.totalMemory() - runtime.freeMemory();
            long objsize = delta(usedmemAfterFillArr, usedmemAfterArrInit, size);
            System.out.println(supllier.get().getClass().getName()+" objSize = " + objsize);
        }
    }

    static void gcInvoke() throws Exception {
        System.gc();
        Thread.sleep(200);
    }

    /**
     * @param l1   - mem after
     * @param l2   - mem before
     * @param size
     * @return - one element in bytes
     */
    static long delta(long l1, long l2, int size) {
        return Math.round(((double) l1 - l2) / size);
    }

    private static class MyClass {
        private int i = 0;
        private long l = 1;
    }
}
