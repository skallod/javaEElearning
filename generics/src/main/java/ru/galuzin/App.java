package ru.galuzin;

//import ru.galuzin.generics.Comedy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ru.galuzin.generics.Maximum;
import ru.galuzin.generics.Radio;
import ru.galuzin.generics.radio_item.Album;
import ru.galuzin.generics.radio_item.RadioItem;
import ru.galuzin.generics.radio_item.Resident;
import ru.galuzin.generics.radio_item.Song;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );
//        Radio radio = new Comedy();
//        radio.information();
        Resident resident = new Resident<Integer, Album, Song<Album>>("fds",23);
        Album album = new Album();
        Song song = new Song<Album>("pug daddy","coliforn", (long) 2333);
        song.setEntity(album);
        resident.setEntity(song);
        XStream xStream = new XStream(new DomDriver());
        String xmlStr = xStream.toXML(resident);
        try(FileWriter fw = new FileWriter("c:\\temp\\resident.xml",false);
            BufferedWriter bw = new BufferedWriter(fw);){
            bw.write(xmlStr);
        }
        Resident resident1 = (Resident)xStream.fromXML(xmlStr);
        System.out.println("resident1 = " + resident1);
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
        method1(Song.class.newInstance());
        new Maximum().information();
    }

    private static void method1(Song<Album> song){
        System.out.println(""+song.hashCode());
    }
}
