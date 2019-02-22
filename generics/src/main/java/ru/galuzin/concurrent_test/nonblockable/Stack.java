package ru.galuzin.concurrent_test.nonblockable;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Stack<T> {

    AtomicReference<Node<T>> head = new AtomicReference<>();

    public void put(T element){

        while (true){
            Node<T> headNode = head.get();
            if(head.compareAndSet(headNode,new Node<>(element,headNode))){
                System.err.println("put "+element);
                return;
            }
        }
    }

    public T get(){
        while (true) {
            Node<T> headNode = head.get();
            if(headNode==null){
                System.err.println("get "+null);
                return null;
            }
            if (head.compareAndSet(headNode, headNode.next)) {
                System.err.println("get "+headNode.element);
                return headNode.element;
            }
        }
    }

    private class Node<T>{
        final T element;
        final Node<T> next;

        public Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.put(1);
        stack.put(2);
        stack.put(3);
        stack.get();
        stack.get();
        stack.get();
        stack.get();
        CountDownLatch latch = new CountDownLatch(10);
        Random random = new Random(37);
        for (int i = 0; i < 5; i++) {
            new Thread(()->{latchUtil(latch);stack.put(random.nextInt());}).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(()->{latchUtil(latch);stack.get();}).start();
        }
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void latchUtil(CountDownLatch latch){
        try {
            latch.countDown();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
