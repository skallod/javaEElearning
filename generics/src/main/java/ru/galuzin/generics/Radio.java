package ru.galuzin.generics;

import ru.galuzin.generics.radio_item.RadioItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 17.04.2016.
 */
public abstract class Radio <T extends RadioIF, U extends RadioItem> implements RadioIF{

    T radio;
    List<U> itemsList = new LinkedList<U>();

    public T getRadio() {
        return radio;
    }

    public void setRadio(T radio) {
        this.radio = radio;
    }

    public void add (U item){
        itemsList.add(item);
    }

    public List<U> getItemsList(){
        return itemsList;
    }

    public abstract void information();
}
