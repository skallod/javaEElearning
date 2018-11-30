package ru.galuzin;

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
