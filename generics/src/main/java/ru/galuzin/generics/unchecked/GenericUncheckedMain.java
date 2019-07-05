package ru.galuzin.generics.unchecked;

import java.util.HashMap;

public class GenericUncheckedMain {
    public static void main(String[] args) {
        HashMap<Class, Handler> map = new HashMap<>();
        map.put(B.class, new HandlerImp());
        B b = new B();
        m1(b, map);
    }
    @SuppressWarnings("unchecked")
    static void m1(A a, HashMap<Class,Handler> map){
        Handler<A> h = map.get(a.getClass());
        h.handle(a);
    }
}
class A{
//    String getTrav(){
//        return "A";
//    }
}
class B extends A{
    String getTrav(){
        return "B";
    }
}
interface Handler<P extends A>{
    void handle (P o);
}
class HandlerImp implements Handler<B>{
    @Override
    public void handle(B o) {
        System.out.println("o="+o.getTrav());
    }
}