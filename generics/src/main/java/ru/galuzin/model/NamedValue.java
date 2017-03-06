package ru.galuzin.model;

/**
 * Created by galuzin on 10.01.2017.
 */
public class NamedValue{
    private static final long serialVersionUID = -817240694338367635L;
    private String name;
    private String value;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public NamedValue() {
    }

    public NamedValue(String name, String value) {
        this.name = name;
        this.value = value;
    }


}
