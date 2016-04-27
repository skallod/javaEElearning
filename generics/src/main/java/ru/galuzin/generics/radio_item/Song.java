package ru.galuzin.generics.radio_item;

import ru.galuzin.generics.BaseEntityContainer;

/**
 * Created by User on 17.04.2016.
 */
public class Song<T> extends RadioItem {

    String artirstName;
    String songName;
    Long length;
    BaseEntityContainer<T> entity;

    public void setEntity(BaseEntityContainer<T> entity) {
        this.entity = entity;
    }

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

    public void setEntity(T object){
        if(entity==null){
            entity = new BaseEntityContainer<T>() {
            };
        }
        entity.setEntity(object);
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
