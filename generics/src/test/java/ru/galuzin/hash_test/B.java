package ru.galuzin.hash_test;

public class B {
    String a;
    int b;

    B(String a, int b){
        this.a=a;
        this.b=b;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
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
