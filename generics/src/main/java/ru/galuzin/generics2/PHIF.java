package ru.galuzin.generics2;

interface PHIF<T extends BP> {
    String findName(T t);
}
