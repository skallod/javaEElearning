package ru.galuzin.school.task1;

import java.util.ArrayList;
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
        int amazingCount=0;
        for(int i=19;i<=6100000;i++){
            Integer[] ciphers = split(i);
            Set<Integer> indexes = new HashSet<Integer>();
            System.out.println("check = " + i);
            for(int j=2; j<=ciphers.length;j++){
                iteration(j,ciphers,indexes);
            }
            if(ciphers.length==indexes.size()){
                System.out.println("amazing number = " + i);
                amazingCount++;
            }
        }
        System.out.println("amazingCount = " + amazingCount);
    }


    static void iteration(int length, Integer[] arr, Set<Integer> indexes){
        for(int i=0; i<arr.length; i++){
            for(int k=1; k<arr.length; k++){
                if((k-i)==(length-1)){
                    int localsum = 0;
                    HashSet<Integer> localset = new HashSet<>();
                    for(int s=i; s<=k; s++){
                        localsum+=arr[s];
                        localset.add(s);
                    }
                    if(localsum==10){
                        indexes.addAll(localset);
                    }
                }
            }
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

}


