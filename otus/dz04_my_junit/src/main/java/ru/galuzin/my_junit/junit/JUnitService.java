package ru.galuzin.my_junit.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class JUnitService {

    public static void executeTest(String className) throws Exception {
        Class<?> aClass = Class.forName(className);
        ValueHolder<Optional<Method>> beforeMethod = new ValueHolder<>(Optional.empty());
        ValueHolder<List<Method>> testMethodList = new ValueHolder<>(Collections.emptyList());
        ValueHolder<Optional<Method>> afterMethod = new ValueHolder<>(Optional.empty());
        Arrays.asList(aClass.getDeclaredMethods()).forEach(method -> {
            Arrays.asList(method.getDeclaredAnnotations()).forEach(annotation -> {
                if(annotation instanceof Before){
                    beforeMethod.setValue(Optional.of(method));
                }else if(annotation instanceof Test){
                    if(testMethodList.getValue().isEmpty()){
                        testMethodList.setValue(new ArrayList<>());
                    }
                    testMethodList.getValue().add(method);
                }else if(annotation instanceof After){
                    afterMethod.setValue(Optional.of(method));
                }
            });
        });
        Object o = aClass.newInstance();
        executeMethod(beforeMethod.getValue(), o);
        testMethodList.getValue().forEach(method -> executeMethod(Optional.of(method), o));
        executeMethod(afterMethod.getValue(),o);
    }

    private static void executeMethod(Optional<Method> methodOptional, Object o) {
        methodOptional.ifPresent(method -> {
            try {
                method.invoke(o);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
