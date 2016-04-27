package ru.galuzin.generics.radio_item;

import ru.galuzin.generics.BaseEntityContainer;

import java.math.BigDecimal;

/**
 * Created by User on 17.04.2016.
 */
public class Resident<T extends Number, V /*album*/ , E /*song*/ extends Song<V>> extends RadioItem {
    String residentName;
    T salary;//salary
    E entity;//song

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

    public void setEntity (E ent){
        entity = ent;
    }

    public Class<Song<T>> getEntityClass(){
        return (Class<Song<T>>)entity.getClass();
    }

//    public class ResidentRowEditor extends RowEditor{
//
//        String data;
//
//        public ResidentRowEditor(int index) {
//            super(index);
//        }
//
//        public void updateData(String data) {
//            this.data = data;
//        }
//    }
}
