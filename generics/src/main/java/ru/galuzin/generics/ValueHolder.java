package ru.galuzin.generics;

public class ValueHolder<T> {
    private static final long serialVersionUID = -5533453831623432182L;

    private T value;

    public ValueHolder() {
        // no-op
    }

    public ValueHolder(final T aValue) {
        value = aValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T val) {
        T oldValue = value;
        value = val;
    }
}
