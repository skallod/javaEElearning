package ru.galuzin.rail_stations;

import org.json.JSONObject;
import ru.galuzin.App;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.DoubleBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by galuzin on 09.06.2016.
 */
public class StationListHelper {

    public List<Station> stationList = new ArrayList<Station>();
    public String basePath;
    String slh_filename="all_rail_stations_withoutCity.txt";

    public void loadFromFile() throws IOException {
        InputStream fis = new FileInputStream(basePath+slh_filename);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        String line;
        int counter = 0;
        while(( line=br.readLine())!=null){
            String [] args = line.split(";");
            Station station = new Station(args[0],args[1]);
            try {
                station.lantitude = Double.valueOf(args[2]);
            }catch (NumberFormatException nfe){
                station.lantitude = null;
            }
            try{
                station.longtitude = Double.valueOf(args[3]);
            }catch (NumberFormatException nfe){
                station.longtitude=null;
            }
            stationList.add(station);
            counter++;
        }
        br.close();
        System.out.println("stationList = " + stationList);
        System.out.println("counter = " + counter);
    }

    public void write() throws IOException {
        OutputStream fis = new FileOutputStream(basePath +slh_filename);
        OutputStreamWriter isr = new OutputStreamWriter(fis, Charset.forName("UTF-8"));
        BufferedWriter fw = new BufferedWriter(isr);
        StringBuilder sb = new StringBuilder();
        for(Station station : stationList){
            sb.append(station.stationCode);
            sb.append(";");
            sb.append(station.stationName);
            sb.append(";");
            sb.append(station.lantitude);
            sb.append(";");
            sb.append(station.longtitude);
            sb.append("\n");
        }
        fw.write(sb.toString());
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        StationListHelper slh = new StationListHelper();
        slh.basePath= "c:\\temp\\1\\";
        slh.loadFromFile();
        slh.write();
    }

}
