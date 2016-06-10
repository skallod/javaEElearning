package ru.galuzin.rail_stations;

/**
 * Created by galuzin on 09.06.2016.
 */
public class Station {
    public String stationCode;
    public String stationName;
    public Double lantitude;
    public Double longtitude;
    public Station(String a , String b){
        stationCode = a;
        stationName = b;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationCode='" + stationCode + '\'' +
                ", stationName='" + stationName + '\'' +
                ", lantitude=" + lantitude +
                ", longtitude=" + longtitude +
                '}';
    }
}
