package ru.galuzin.generics.test_oracle;

/**
 * Created by User on 17.04.2016.
 */
public class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    public static void main(String[] args) {
        MyNode mn = new MyNode(5);
        Node n = mn;            // A raw type - compiler throws an unchecked warning
//        n.setData("Hello");
//        Integer x = mn.data;    // Causes a ClassCastException to be thrown.
    }
}
