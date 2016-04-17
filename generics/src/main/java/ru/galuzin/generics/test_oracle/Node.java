package ru.galuzin.generics.test_oracle;

/**
 * Created by User on 17.04.2016.
 */
public class Node<T> {

    public T data;

    public Node(T data) { this.data = data; }

    public void setData(T data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}
