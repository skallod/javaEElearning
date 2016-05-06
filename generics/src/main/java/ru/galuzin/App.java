package ru.galuzin;

//import ru.galuzin.generics.Comedy;
import ru.galuzin.generics.Maximum;
import ru.galuzin.generics.Radio;
import ru.galuzin.generics.radio_item.Album;
import ru.galuzin.generics.radio_item.RadioItem;
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

        if(song.getClass().equals(Song.class)){
            System.out.println("test1");
        }
        if(song.getClass()==(Song.class)){
            System.out.println("test2");
        }
        if(song.getClass().equals(RadioItem.class)){
            System.out.println("test3");
        }

            System.out.println("test "+ RadioItem.class.getName());
            System.out.println("test "+ song.getClass().getName());

//        if(song.getClass()==(RadioItem.class)){
//            System.out.println("test4");
//        }
        Object obj1 = null;
//        obj1.toString().equals("");
        if(obj1==null || obj1.toString().equals("")){
            System.out.println("or test");
        }

        new Maximum().information();
    }
}
