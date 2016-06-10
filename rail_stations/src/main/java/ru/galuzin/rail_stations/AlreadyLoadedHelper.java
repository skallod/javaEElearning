package ru.galuzin.rail_stations;

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
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by galuzin on 10.06.2016.
 */
public class AlreadyLoadedHelper {
    public Set<String> set = new LinkedHashSet<String>();
    public String basePath;
    String fileName = "already_loaded.txt";
    public void read() throws IOException {
        InputStream fis = new FileInputStream(basePath+fileName);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        String line;
        int counter = 0;
        while(( line=br.readLine())!=null){
            set.add(line);
            counter++;
        }
        System.out.println("already loaded set = " + set);
        System.out.println("counter = " + counter);
        br.close();

    }
    public void write() throws IOException {

        OutputStream fis = new FileOutputStream(basePath +fileName);
        OutputStreamWriter isr = new OutputStreamWriter(fis, Charset.forName("UTF-8"));
        BufferedWriter fw = new BufferedWriter(isr);
        StringBuilder sb = new StringBuilder();
        for(String str : set){
            sb.append(str);
            sb.append("\n");
        }
        fw.write(sb.toString());
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        AlreadyLoadedHelper alh = new AlreadyLoadedHelper();
        alh.basePath = "c:\\temp\\";
        alh.read();
        alh.write();
    }
}
