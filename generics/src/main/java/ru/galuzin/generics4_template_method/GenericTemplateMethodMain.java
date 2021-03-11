package ru.galuzin.generics4_template_method;

import java.util.UUID;

public class GenericTemplateMethodMain {

    public static void main(String[] args) {
        final Container<UUID> c = new Container<>();
        c.test();
        new Container1().test();
    }


    static class Container<K> {
        Deserializer<K> getD () {
            return (Deserializer<K>) new StringDeserializer();
        }

        void test () {
            test1(getD());
        }

        void test1 (Deserializer<K> d) {
            System.out.println("d.getClass().getName() = " + d.getClass().getGenericInterfaces()[0]);
            //ResolvableType.forClass(getClass()).getSuperType().resolveGeneric(0)
        }
    }

    static class Container1 extends Container<UUID> {
        @Override
        Deserializer<UUID> getD () {
            return new JsonDeserializer<>(java.util.UUID.class);
        }
    }

    static class Deserializer<T> { }

    static class StringDeserializer extends Deserializer<String> {}

    static class JsonDeserializer<T> extends Deserializer<T> {
        JsonDeserializer(Class<T> tClass) {

        }
    }
}
