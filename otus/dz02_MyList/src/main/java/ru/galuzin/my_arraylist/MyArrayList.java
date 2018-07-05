package ru.galuzin.my_arraylist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: LEONID
 * Date: 01.07.18
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public class MyArrayList<T> implements List<T> {

    Object [] array = new Object[3];

    int capacity = 3;

    int size = 0;

    public MyArrayList() {

    }

    @Override
    public int size() {
        return size;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEmpty() {
        return size==0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterator<T> iterator() {
        return new MyListIterator<T>(0);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object[] toArray() {
        if(size==0){
            return new Object[0];
        }
        Object[] objects = new Object[size];
        System.arraycopy(array,0,objects,0,size);
        return objects;
    }

    @Override
    public <T1 extends Object> T1[] toArray(T1[] a) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean add(T t) {
        checkCapacity();
        array[size] = t;
        size++;
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    void checkCapacity(){
        if(size==capacity){
            Object[] dest = new Object[capacity+(capacity>>1)];
            System.arraycopy(array,0,dest,0,size);
            array = dest;
            capacity = array.length;
        }
    }

    @Override
    public boolean remove(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clear() {
        size=0;
    }

    @Override
    public T get(int index) {
        checkSize(index);
        return (T)array[index];  //To change body of implemented methods use File | Settings | File Templates.
    }

    void checkSize(int index){
        if(index>=size){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T set(int index, T element) {
        checkSize(index);
        T old = (T)array[index];
        array[index] = element;
        return old;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(int index, T element) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T remove(int index) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int indexOf(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator<T>(0);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyListIterator<T>(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return "MyArrayList{" +
                "array=" + Arrays.toString(array) +
                ", capacity=" + capacity +
                ", size=" + size +
                '}';
    }

    @Override
    public int hashCode() {
        int result=1;
        for(T obj :this){
           result=result*31+obj.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Objects.requireNonNull(obj);
        if(!(obj instanceof MyArrayList)) return false;
        MyArrayList test = (MyArrayList)obj;
        if(test.size()!=this.size())return false;
        for(int i=0; i<this.size();i++) {
            if (!test.get(i).equals(get(i))) return false;
        }
        return true;
    }

    private class MyListIterator<T> implements ListIterator<T> {

        int cursor;
        int lastReturned=-1;

        MyListIterator(int cursor){
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor<size;
        }

        @Override
        public T next() {
            T result = (T)array[cursor];
            lastReturned = cursor;
            cursor++;
            return result;
        }

        @Override
        public boolean hasPrevious() {
            return lastReturned>0;
        }

        @Override
        public T previous() {
            checkLastReturned();
            T result = (T)array[lastReturned-1];
            lastReturned--;
            cursor--;
            return result;
        }

        @Override
        public int nextIndex() {
            return lastReturned++;
        }

        @Override
        public int previousIndex() {
            return lastReturned--;
        }

        @Override
        public void remove() {
            checkLastReturned();
            Object[] dest = new Object[capacity];
            System.arraycopy(array,0, dest,0,lastReturned);
            System.arraycopy(array,cursor,dest,lastReturned,size-cursor);
            array = dest;
            size--;
            cursor--;
            lastReturned=-1;
        }

        @Override
        public void set(T t) {
            checkLastReturned();
            array[lastReturned] = t;
        }

        @Override
        public void add(T t) {
            System.out.println("add called");
        }

        void checkLastReturned(){
            if(lastReturned<0){
                throw new IllegalStateException();
            }
        }
    }
}
