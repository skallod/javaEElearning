package ru.galuzin;

//import ru.galuzin.generics.Comedy;
import ru.galuzin.generics.Maximum;
import ru.galuzin.generics.Radio;
import ru.galuzin.generics.radio_item.Album;
import ru.galuzin.generics.radio_item.Resident;
import ru.galuzin.generics.radio_item.Song;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
//        Radio radio = new Comedy();
//        radio.information();
        Resident resident = new Resident<Integer, Album, Song<Album>>("fds",23);
        Album album = new Album();
        Song song = new Song<Album>("pug daddy","coliforn", (long) 2333);
        song.setEntity(album);
        resident.setEntity(song);
        System.out.println("class ="+resident.getEntityClass());
//        Resident.RowEditor rowEditor = new resident.ResidentRowEditor(0);


        new Maximum().information();
    }
}
