package ru.galuzin.my_junit;

import ru.galuzin.my_junit.junit.JUnitService;

public class Main {
    public static void main(String[] args) throws Exception{
        JUnitService.executeTest("ru.galuzin.my_junit.tested.A");
    }
}
