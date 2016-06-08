package ru.galuzin;

import com.google.common.collect.Maps;
import com.google.common.primitives.Doubles;
import org.json.JSONObject;
import ru.galuzin.rail_stations.Helper;
import ru.galuzin.rail_stations.JsonReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{

    static String gps;
    static String stationCode="2044041";
    static String stationName="ЖЕРЕБЦОВО";
    static String filePath = "c:\\temp\\";


    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        System.out.println( "Hello World!" );
        defineGps();
        //getAddressByGps();
        System.out.println(""+Locale.getDefault());
//        try {
//            FileReader fileWriter =  new FileReader(new File("c:\\temp\\stationsList5.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    private static void  defineGps() throws IOException, ClassNotFoundException {
        final String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        final Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения

        //params.put("lang","ru-RU");
        params.put("language", "ru");
        params.put("address", "железнодорожная станция "+stationName);//железнодорожная станция БАЛАХНА");//"Россия, Москва, улица Поклонная, 12");// адрес, который нужно геокодировать
        final String url = baseUrl + '?' + Helper.encodeParams(params);// генерируем путь с параметрами
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
        JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        System.out.println("response = " + response);
        // как правило наиболее подходящий ответ первый и данные о координатах можно получить по пути
        // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        JSONObject location = response.getJSONArray("results").getJSONObject(0);
        location = location.getJSONObject("geometry");
        location = location.getJSONObject("location");
        final double lng = location.getDouble("lng");// долгота
        final double lat = location.getDouble("lat");// широта
        gps=Double.toString(lng)+","+Double.toString(lat);
        System.out.println(gps);// итоговая широта и долгота
        writeObject(stationCode,response);
        //JSONObject response1 = readObject(stationCode);
        //System.out.println("respser1 = " + response1);
    }

    static void getAddressByGps() throws IOException {
        final String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        final Map<String, String> params = Maps.newHashMap();
        params.put("language", "ru");// язык данных, на котором мы хотим получить
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
        // текстовое значение широты/долготы, для которого следует получить ближайший понятный человеку адрес, долгота и
        // широта разделяется запятой, берем из предыдущего примера
        params.put("latlng", gps);//"50.6468200,13.8293838");//"55.735893,37.527420");
        final String url = baseUrl + '?' + Helper.encodeParams(params);// генерируем путь с параметрами
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        writeObject(stationCode+"_reverse",response);
        // как правило, наиболее подходящий ответ первый и данные об адресе можно получить по пути
        // //results[0]/formatted_address
        final JSONObject location = response.getJSONArray("results").getJSONObject(0);
        final String formattedAddress = location.getString("formatted_address");
        System.out.println(formattedAddress);// итоговый адрес
    }

    private static void writeObject(String stationCode, JSONObject _jsonObject) throws IOException{
        //FileOutputStream out = new FileOutputStream("c:\\temp\\"+stationCode);
        //ObjectOutputStream oos = new ObjectOutputStream(out);
        //oos.defaultWriteObject();
        //oos.writeChars(_jsonObject.toString());
        //FileWriter fw = new FileWriter("c:\\temp\\"+stationCode,false);
        OutputStream fis = new FileOutputStream(filePath+stationCode);
        OutputStreamWriter isr = new OutputStreamWriter(fis, Charset.forName("UTF-8"));
        BufferedWriter fw = new BufferedWriter(isr);
        fw.write(_jsonObject.toString());
        fw.close();
    }

    private static JSONObject readObject( String stationCode) throws ClassNotFoundException, IOException {
        //FileInputStream is = new FileInputStream("c:\\temp\\"+stationCode);
        //ObjectInputStream ois = new ObjectInputStream(is);
        //ois.defaultReadObject();
        //return new JSONObject(ois.readUTF());
        InputStream fis = new FileInputStream(filePath+stationCode);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        JSONObject jsObj1 = new JSONObject(br.readLine());
        br.close();
        return jsObj1;
    }
}
/*
      82971e0b-3094-4646-afac-4b76de68b462;2028036;{ru=САХАЛИНКА};;Россия
23e6976d-bb8f-40ff-b88b-9cff12ebac86;2028035;{ru=САЙГА};;Россия
ac1227b9-90ea-4db1-8c83-4a3c965c826f;2209459;{ru=ВЕРХНЕЗГАРЬ};;Украина
ca9ba80f-8786-47e3-a5f2-f6b129c9cdda;2028034;{ru=ТАСКАЕВО};;Россия
e3b1e5cd-76a2-43ca-82ab-083ffe2c3968;2028033;{ru=ЮРГА 2};;Россия
083717c2-059e-4f79-8fe8-9bcb3f729dde;2028048;{ru=КУРЕГЕШ};;Россия
429505cd-6177-4a08-946b-a6099a2c49bc;2028049;{ru=ОСТРОВСКАЯ};;Россия
13b13372-2c66-40e5-8f22-973511e53549;2028041;{ru=ШИШИНО};;Россия
51e042c5-2d12-458f-b659-dd2f161d38f6;2218577;{ru=ПЛАТФОРМА 40 КМ};;Украина
1810734e-9cfc-4016-a653-05b3fbbaa201;2028040;{ru=КИСЕЛЕВСК ПАСС};;Россия
9f8bfde6-51a6-449a-a312-9a6c811d3a68;2300106;{ru=ВЕРЕЖАНЫ};;Молдова
f5b2b114-db05-42ea-a6d7-099770f961ed;2028043;{ru=ЮРЬЕВКА};;Россия
b222dc1f-3a09-409f-935a-af88d205d2dc;2300107;{ru=РАЗ'ЕЗД 208 КМ};;Молдова
9b7aaaca-15ad-4f71-89c8-5a98d6cb1b7f;2028042;{ru=КИСЕЛЕВСК};;Россия
86341194-8293-43de-9635-cfbd193cd08c;2218574;{ru=СТРЫХАНКА};;Украина
6db245ce-2656-411b-a329-049859f880a0;2028045;{ru=ТОПКИ};;Россия
b3d3f4f7-d059-44f1-b8ec-b0b997901aed;2028044;{ru=О.П.431 КМ};;Россия
* */