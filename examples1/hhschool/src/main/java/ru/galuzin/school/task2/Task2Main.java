package ru.galuzin.school.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * Определим функцию P(n,k) следующим образом: P(n,k) = 1, если n может быть представлено в виде суммы k простых чисел (простые числа в записи могут повторяться, 1 не является простым числом) и P(n,k) = 0 в обратном случае.
 К примеру, P(10,2) = 1, т.к. 10 может быть представлено в виде суммы 3 + 7 или 5 + 5, а P(11,2) = 0, так как никакие два простых числа не могут дать в сумме 11.
 Определим функцию S(n) как сумму значений функции P(i,k) для всех i и k, таких что 1≤i≤n, 1≤k≤n. Таким образом, S(2) = P(1,1) + P(2,1) + P(1,2) + P(2,2) = 1, S(10) = 20, S(1000) = 248838.

 Найдите S(11147).
 */
public class Task2Main {
    static final Logger log = Logger.getLogger(Task2Main.class);
    static Integer[] simples;
    static final int SLOW_VER=2;
    static long iterations=0;
    public static void main(String[] args) {
//        URL resource = Task2Main.class.getResource("log4j.properties");
//        PropertyConfigurator.configure(resource);
//        String str = Arrays.toString(((URLClassLoader)Task2Main.class.getClassLoader()).getURLs());
//        System.out.println("str = " + str);
        simples = loadSimples(11147);

//        combtest();
        int evristMinPos =maxdelta(simples);
        AtomicInteger allsum = new AtomicInteger();
        allsum.set(1);
        long time = System.nanoTime();
        for(int i=3; i <= 11147; i++)
        //int i=902;
        {
            Integer[] subSimple = subSimple(i,simples);
            log.debug("Arrays.asList(subSimple) = " + Arrays.asList(subSimple));
            int evristStartIdx = evristMinPos;
            boolean firstfound = true;
            for(int j=1; j<=11147; j++)
            {
                log.debug("p("+i+ ";" + j+")");
                if(j==1){
                    if(subSimple.length>0 && subSimple[subSimple.length-1]==i){
                        log.debug("amazing ["+i+"]");
                        allsum.incrementAndGet();
                        continue;
                    }
                }
                if(j*2>i){
                    break;
                }
                if(j<=20){
                    pFunc(i,j,subSimple,allsum,false);
                }else {
                    pFunc(i, j, subSimple, allsum, true);
//                    if ( == 1) {
//                        if (firstfound) {
//                            evristStartIdx = (j < evristMinPos) ? j : evristMinPos;
//                            firstfound = false;
//                        }
//                    }
                }
            }
//            log.debug("slow coutn "+evristStartIdx);
//            evristStartIdx =10;
//            //TODO Set less 20  last sum on 1000 248797 248839_1 248806_2
//            for(int k=2; k<evristStartIdx; k++){
//                if(k*2>i){
//                    break;
//                }
//                log.debug("slow p("+i+ ";" + k+")");
//                pFunc(i,k,subSimple,allsum,false);
//            }
        }
        log.debug("slow ver "+SLOW_VER);
        log.debug("allsum = " + allsum);
        log.debug("timing = " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-time));
        log.debug("iterations = " + iterations);
    }

    private static int maxdelta(Integer[] simples/*, int maxvalue*/) {
        int delta = 0 ;
        int minPrime = 0;
        for(int i=0; i<=simples.length; i++){
            if(i+1<simples.length){
                int locdelta= simples[i+1]-simples[i];
                if(locdelta>delta){
                    delta=locdelta;
                }
            }
        }
        log.debug("max delta "+delta);
//        log.debug("min prime "+minPrime);
        return delta;
    }

    private static Integer[] subSimple(int i, Integer[] simples) {
        ArrayList<Integer> integers = new ArrayList<Integer>();
        for(int k=0; k<simples.length ; k++){
            if(simples[k]<=i){
                integers.add(simples[k]);
            }
        }
        return integers.toArray(new Integer[integers.size()]);
    }


    private static int pFunc(int i, int j, Integer[] subsimle, final AtomicInteger allsum, boolean evrist) {

        try {
            if(evrist) {
                Pair[] pairs = new Pair[j];
                for (int k = 0; k < j; k++) {
                    pairs[k] = new Pair();
                }
                evristAlg(subsimle, j, 0, 0, pairs, i);
            }else {
                switch (SLOW_VER){
                    case 1:
                        slowAlg1(subsimle, j, 0, 0, new int[j], i);
                        break;
                    case 2:
                        slowAlg2(subsimle, j, 0, 0, new int[j], i);
                        break;
                }
            }
        }catch (FoundException fe){
            allsum.incrementAndGet();
            return 1;
        }
        return 0;
    }

    static Integer[] loadSimples(int seed){
        ArrayList<Integer> integers = new ArrayList<Integer>();
        integers.add(2);
        integers.add(3);
        for(int number = 5 ; number<=seed ; number+=2){
            if(isPrime(number)){
                integers.add(number);
            }
        }
        log.debug("integers = " + integers);
        return integers.toArray(new Integer[integers.size()]);
    }
    static void slowAlg1(Integer[] arr, int len, int startPosition,int ipos, int[] result,int check){
        iterations++;
        if (len == 0){
            int sum =0;
            for (int i = 0; i < result.length; i++) {
                sum+=result[i];
            }
            if(sum==check){
                //log.debug("amazing "+Arrays.toString(result));
                throw new FoundException();
            }
            return;
        }
        for (int i = ipos; i < arr.length; i++){
            result[startPosition] = arr[i];
            slowAlg1(arr, len - 1, startPosition + 1, i, result, check);
        }
    }
    static void slowAlg2(Integer[] arr, int len, int startPosition,int ipos, int[] result,int check){
        iterations++;
        for (int i = ipos; i < arr.length; i++){
            result[startPosition] = arr[i];
            //log.debug("iterate "+Arrays.toString(result));
            int sum = arrSum(result);
            if(sum==check){
                //log.debug("amazing "+Arrays.toString(result));
                throw new FoundException();
            }else if(sum>check){
                //log.debug("iterate return "+Arrays.toString(result));
                if(ipos+1<arr.length)result[startPosition]=arr[ipos+1];
                else result[startPosition]=arr[ipos];
                return;
            }
            if(len-1!=0) {
                slowAlg2(arr, len - 1, startPosition + 1, i, result, check);
            }
            if(i==arr.length-1){
                if(ipos+1<arr.length)result[startPosition]=arr[ipos+1];
                else result[startPosition]=arr[ipos];
                return;
            }
        }
    }
    static void evristAlg(Integer[] arr, int len, int startPosition,int ipos, Pair[] result, int check/*, ValueHolder vh*/){
        iterations++;
        for (int i = ipos; i < arr.length; i++){
            result[startPosition].value = arr[i];

            if(len -1 ==0){
                int sum =0;
                //log.debug(Arrays.toString(result));
                for (int j = 0; j < result.length; j++) {
                    sum+=result[j].value;
                }
                if(sum==check){
                    //log.debug("amazing "+Arrays.toString(result));
                    throw new FoundException();
                }
                else if(sum>check){
                    result[startPosition].updated = true;
                    if (i - 1 >= 0) {
                        result[startPosition].value = arr[i - 1];
                    }
                    //log.debug("updated "+"("+startPosition+";"+result[startPosition].value+")");
                    return ;
                }
            }
            else{
                if((startPosition+1)<result.length && !result[startPosition+1].updated) {
                    evristAlg(arr, len - 1, startPosition + 1/*i*//*+1*/, i, result, check);
                }
                int sum = 0;
                //log.debug(Arrays.toString(result));
                for (int j = 0; j < result.length; j++) {
                    sum+=result[j].value;
                }
                if(sum==check){
                    //log.debug("amazing "+Arrays.toString(result));
                    throw new FoundException();
                }
                else if(sum>check){
                    result[startPosition].updated = true;
                    if (i - 1 >= 0) {
                        result[startPosition].value = arr[i - 1];
                    }
                    return ;
                }
            }
        }
    }

    static boolean isPrime(int n){
        if (n % 2 == 0) return false;
        if (n % 3 == 0) return false;

        int i = 5;
        int w = 2;

        while (i * i <= n) {
            if (n % i == 0) return false;
            i += w;
            w = 6 - w;
        }

        return true;
    }

    static int arrSum(int[] arr){
        int sum = 0 ;
        for (int j = 0; j < arr.length; j++) {
            sum+=arr[j];
        }
        return sum;
    }
// TODO   static int arrSumPair(int[] arr){
//        int sum = 0 ;
//        for (int j = 0; j < arr.length; j++) {
//            sum+=arr[j];
//        }
//        return sum;
//    }

}
//class ValueHolder{
//    int sum=0;
//}
class FoundException extends RuntimeException{
}
class ExitException extends RuntimeException{
}
class Pair {
    int value;
    boolean updated;

    @Override
    public String toString() {
        return "{"+value+'}';
    }
}