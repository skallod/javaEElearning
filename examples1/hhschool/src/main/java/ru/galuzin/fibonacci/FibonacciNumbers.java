package ru.galuzin.fibonacci;

import java.util.Scanner;

public class FibonacciNumbers {

    public static void main(String[] args) {
        fibCyclicBuffer();
        //fibMod10();
        //fibN()
    }

    /**
     * Избежать переполнения памяти
     */
    private static void fibCyclicBuffer() {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        if(n==1){
            System.out.println(1);
        }else {
            long[] arr = new long[10];
            arr[0] = 0;
            arr[1] = 1;
            int i = 2;
            int currPos = 0;
            for (; i <= n; i++) {
                currPos = i%10;
                int prev = (i-1+10)%10;
                int prevPrev = (i-2+10)%10;
                arr[currPos] = arr[prev] + arr[prevPrev];
                arr[currPos]%=m;
            }
            System.out.println(arr[currPos]);
        }
    }

    /**
     * Дано число 1≤n≤10^7, необходимо найти последнюю цифру n-го числа Фибоначчи.
     *
     */
    private static void fibMod10() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if(n==1){
            System.out.println(1);
        }else {
            long[] arr = new long[n+1];
            arr[0] = 0;
            arr[1] = 1;
            for (int i = 2; i <= n; i++) {
                arr[i] = arr[i - 1] + arr[i - 2];
                arr[i]%=10;
            }
            System.out.println(arr[n]);
        }
    }

    private static void fibN() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if(n==1){
            System.out.println(0);
        }else {
            int[] arr = new int[n+1];
            arr[0] = 0;
            arr[1] = 1;
            for (int i = 2; i <= n; i++) {
                arr[i] = arr[i - 1] + arr[i - 2];
            }
            System.out.println(arr[n]);
        }
    }
}
