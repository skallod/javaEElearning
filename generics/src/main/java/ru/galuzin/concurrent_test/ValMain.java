package ru.galuzin.concurrent_test;

/**
 * Created by galuzin on 24.08.2016.
 */
public class ValMain {
    public static void main(String[] args) {

    }
    private void case1(){
        Runnable first = new Runnable() {
            @Override
            public void run() {
                while (true){
                    CommonObject.getInstance().setParams("Leonid","Galuzin",1111111);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        };
        Runnable second = new Runnable() {
            @Override
            public void run() {
                while (true){
                    CommonObject.getInstance().setParams("Kiseleva","Anastasiya",2222);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        };
        Runnable third = new Runnable() {
            @Override
            public void run() {
                while (true){
                    CommonObject.getInstance().setParams("Antonova","Olga",33333);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        };
        new Thread(first).start();
        new Thread(second).start();
        new Thread(third).start();
    }
    public void case2(){

    }
}
