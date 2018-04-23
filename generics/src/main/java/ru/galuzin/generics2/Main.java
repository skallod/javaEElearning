package ru.galuzin.generics2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<BP> bps = new ArrayList<>();
        bps.add(new P());
        PHIF phif = new PH();
        phif.findName(bps.get(0));
    }
}
