package ru.galuzin.hash_test;

/**
 * good realization
 */
public class A {
    String a;
    int b;

    A(String a, int b) {
        this.a = a;
        this.b = b;
    }

    public boolean equals(Object o){
        if(o==null || !(o instanceof A)){
            return false;
        }
        A h = (A)o;
        if((a==null && h.a!=null)||(a!=null && h.a==null)){
            return false;
        }
        if(a==null && h.a==null && b==h.b){
            return true;
        }
        if(a.equals(h.a) && b==h.b){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31*result + b;
        if(a!=null){
            result = 31*result + a.hashCode();
        }
        return result;
    }

}