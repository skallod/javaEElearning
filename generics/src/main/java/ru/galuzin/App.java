package ru.galuzin;

import ru.galuzin.generics.Comedy;
import ru.galuzin.generics.Maximum;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        new Comedy().information();
        new Maximum().information();
    }
}
