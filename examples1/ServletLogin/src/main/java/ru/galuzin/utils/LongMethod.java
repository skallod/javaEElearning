package ru.galuzin.utils;

import java.io.PrintWriter;

import static java.lang.System.currentTimeMillis;

/**
 * Created by galuzin on 19.02.2017.
 */
public class LongMethod {
    public static double longTimeOperation(){
        double result= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.random()*Math.PI)))),2d)))),2d)),2d));
        for(int i=0; i<10000000;i++){
            result+= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.random()*Math.PI)))),2d)))),2d)),2d));
        }
        return result;
    }

    public static void invokeAndLog(PrintWriter pw, String message ){
        long curtiME= currentTimeMillis();
        double result = longTimeOperation();
        if(pw!=null)pw.write("<br>result="+result);
        long delTime = System.currentTimeMillis()-curtiME;
        if(pw!=null)pw.write("<br>"+message+"="+delTime);
        System.out.println(message+delTime);
    }
}
