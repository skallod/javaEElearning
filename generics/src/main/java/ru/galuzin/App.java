package ru.galuzin;

import ru.galuzin.generics.Comedy;
import ru.galuzin.generics.Maximum;
import ru.galuzin.generics.Radio;
import ru.galuzin.generics.radio_item.Resident;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Radio radio = new Comedy();
        radio.information();
        Resident resident = new Resident<Integer>("fds",23);
        Resident.RowEditor rowEditor = new resident.ResidentRowEditor(0);

        new Maximum().information();
    }
}
