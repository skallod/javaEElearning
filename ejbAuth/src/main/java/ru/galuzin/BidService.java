package ru.galuzin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by galuzin on 22.11.2016.
 */
public class BidService {
    public void addBid(Bid bid){
        FileWriter fw = null;
        try {
            fw = new FileWriter("c:\\temp\\bids.txt" , true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(bid.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fw!=null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
