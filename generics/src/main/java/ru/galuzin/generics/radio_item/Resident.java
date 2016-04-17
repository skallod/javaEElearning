package ru.galuzin.generics.radio_item;

import java.math.BigDecimal;

/**
 * Created by User on 17.04.2016.
 */
public class Resident<T extends Number> extends RadioItem {
    String residentName;
    T salary;

    public Resident(String residentName, T salary){
        this.residentName = residentName;
        this.salary = salary;
    }

    public String getResidentName(){
        return residentName;
    }

    public T getSalary(){
        return salary;
    }

    @Override
    public String toString() {
        return "Resident{" +
                "residentName='" + residentName + '\'' +
                ", salary=" + salary.doubleValue() +
                '}';
    }
}
