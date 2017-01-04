package ru.galuzin.attribute_listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Created by User on 31.12.2016.
 */
public class MyAttributeListener implements
        HttpSessionAttributeListener{
    private String counterAttr = "counter";

    @Override
    public void attributeAdded(HttpSessionBindingEvent ev) {
        String currentAttributeName = ev.getName();
        String urlAttr = "URL";
        if(currentAttributeName.equals(counterAttr)){
            Integer currentValueInt = (Integer)ev.getValue();
            System.out.println("? session ???????? ???????="+currentValueInt);
        }else if(currentAttributeName.equals(urlAttr)){
            StringBuffer currentValueStr = (StringBuffer)ev.getValue();
            System.out.println("? ??????? ???????? ???="+currentValueStr);
        }else System.out.println("???????? ????? ????????");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent ev) {
        String currentAttributeName = ev.getName();
        if(currentAttributeName.equals(counterAttr)){
            Integer currentValueInt = (Integer)ev.getValue();
            System.out.println("? ??????? ??????? ???????="+currentValueInt);
        }
    }
}
