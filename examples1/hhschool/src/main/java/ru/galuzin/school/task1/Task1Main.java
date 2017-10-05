package ru.galuzin.school.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Задача 1
 В некоторых числах можно найти последовательности цифр, которые в сумме дают 10. К примеру, в числе 3523014 целых четыре таких последовательности:
 3523014
 3523014
 3523014
 3523014
 Можно найти и такие замечательные числа, каждая цифра которых входит в по крайней мере одну такую последовательность.
 Например, 3523014 является замечательным числом, а 28546 — нет (в нём нет последовательности цифр, дающей в сумме 10 и при этом включающей 5).
 Найдите количество этих замечательных чисел в интервале [1, 6_100_000] (обе границы — включительно).
 */
public class Task1Main {
    public static void main(String[] args) {
        //String[] arr = {"0","5","5"};
        /*Работает чеко ! задать len и размер массива*/
        //combinations2(arr, 2, 0, new Pair[2]);
        int amazingCount=0;
        for(int i=19;i<=6100000;i++){
            Integer[] ciphers = split(i);
            Set<Integer> indexes = new HashSet<Integer>();
            System.out.println("check = " + i);
            for(int j=2; j<=ciphers.length;j++){
                combinations2(ciphers, j, 0, new Pair[j],indexes);
            }
            if(ciphers.length==indexes.size()){
                System.out.println("amazing number = " + i);
                amazingCount++;
            }
        }
        System.out.println("amazingCount = " + amazingCount);
    }

    /**
     * Алгоритм сочетания без повторений
     * @param arr
     * @param len
     * @param startPosition
     * @param result
     * @param indexes
     */
    static void combinations2(Integer[] arr, int len, int startPosition, Pair[] result, Set<Integer> indexes){
        if (len == 0){
            System.out.println(Arrays.toString(result));
            checkSumAndUpdateIndexes(result,indexes);
            return;
        }
        for (int i = startPosition; i <= arr.length-len; i++){
            result[result.length - len] = new Pair(arr[i],i);
            combinations2(arr, len-1, i+1, result,indexes);
        }
    }
    static Integer[] split(int number){
        ArrayList<Integer> ciphers = new ArrayList<Integer>();
        int current = number;
        do {
            int cipher = current % 10;
            ciphers.add(cipher);
            current = current/10;
        }while (current>0);
        System.out.println("ciphers = " + ciphers);
        return ciphers.toArray(new Integer[ciphers.size()]);
    }

    static void checkSumAndUpdateIndexes(Pair[] arr, Set<Integer> indexes){
        int sum =0;
        for (int i=0; i<arr.length;i++){
            int cipher = arr[i].first;
            sum+=cipher;
        }
        if(sum==10){
            for (int i=0; i<arr.length;i++){
                indexes.add(arr[i].second);
            }
        }
    }
}
class Pair{
    public Integer first;
    //index in checked array
    public Integer second;

    public Pair(Integer first, Integer second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "{"+ first + ';' + second + '}';
    }
}



