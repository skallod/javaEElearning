package ru.galuzin.generics;

import ru.galuzin.generics.radio_item.Resident;
import ru.galuzin.generics.radio_item.Song;

/**
 * Created by User on 17.04.2016.
 */
public class Maximum extends Radio<Maximum,Song>{
    private String currentSong = "depesh";
    private Song song ;

    public Maximum(){
        super.setRadio(this);
        super.getItemsList().add(new Song("Prodigy","Breath", 532L));
        super.getItemsList().add(new Song("Оргия праведников","Вперед и вверх", 67L));
    }

    public String getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(String currentSong) {
        this.currentSong = currentSong;
    }

    public void information(){
        System.out.println(""+super.getRadio().getCurrentSong());
        for(Song song : super.getItemsList()){
            System.out.println("song = " + song);
        }
    }
}
