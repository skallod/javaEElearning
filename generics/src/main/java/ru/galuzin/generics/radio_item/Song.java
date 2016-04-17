package ru.galuzin.generics.radio_item;

/**
 * Created by User on 17.04.2016.
 */
public class Song extends RadioItem {

    String artirstName;
    String songName;
    Long length;

    public Song(String artistName, String songName, Long length){
        this.artirstName = artistName;
        this.songName = songName;
        this.length = length;
    }

    public String getArtirstName() {
        return artirstName;
    }

    public String getSongName() {
        return songName;
    }

    public Long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Song{" +
                "artirstName='" + artirstName + '\'' +
                ", songName='" + songName + '\'' +
                ", length=" + length +
                '}';
    }
}
