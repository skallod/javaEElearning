package ru.galuzin.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.galuzin.connection.DBServiceUpdate;

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBServiceUpdate dbService = new DBServiceUpdate()) {
            System.out.println(dbService.getMetaData());
            final String tablename = "tablename_";
            SimpleDateFormat timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

            String segmentName="";

            for (int j = 0; j < 2; j++) {
                boolean isSameSigment;
                do {
                    String newSegmentName = tablename + timestamp.format(new Date());
                    isSameSigment = newSegmentName.equals(segmentName);
                    if (isSameSigment) {
                        Thread.sleep(100);
                    }else {
                        segmentName = newSegmentName;
                    }
                } while (isSameSigment);
                System.out.println("segmentName = " + segmentName);
                dbService.createSegment(segmentName);

                long time = System.nanoTime();
                for (int i = 0; i < 1000; i++) {
                    dbService.generateSegmentData();
                }
                System.out.println("1000 inserts time = " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-time));

            }

            dbService.createView();
            List<String> texts = dbService.selectFromView();
            System.out.println("texts = " + texts.size());
            System.out.println("texts = " + texts);
//            while (true){
//                Thread.sleep(1000);
//            }
//            dbService.addUsers("tully", "sully");
//            System.out.println("UserName with id = 1: " + dbService.getUserName(1));
//            List<String> names = dbService.getAllNames();
//            System.out.println("All names: " + names.toString());
//            List<UsersDataSet> users = dbService.getAllUsers();
//            System.out.println("All users: " + users.toString());
//            dbService.deleteTables();
        }
    }
}
