package ru.galuzin.my_junit.junit;

public class ValueHolder<T>{
    T value;

    public ValueHolder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
