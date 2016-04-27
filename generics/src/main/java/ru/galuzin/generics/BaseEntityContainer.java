package ru.galuzin.generics;

/**
 * Created by galuzin on 27.04.2016.
 */
public abstract class BaseEntityContainer <V> {
    V entity;

    public V getEntity() {
        return entity;
    }

    public void setEntity(V entity) {
        this.entity = entity;
    }
}
