package ru.rearitem;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class HttpClientHelper {
    void sendPost() throws Exception {
        URL url = new URL("http://yoururl.com");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

//        Uri.Builder builder = new Uri.Builder()
//                .appendQueryParameter("firstParam", paramValue1)
//                .appendQueryParameter("secondParam", paramValue2)
//                .appendQueryParameter("thirdParam", paramValue3);
//        String query = builder.build().getEncodedQuery();
//
//        OutputStream os = conn.getOutputStream();
//        BufferedWriter writer = new BufferedWriter(
//                new OutputStreamWriter(os, "UTF-8"));
//        writer.write(query);
//        writer.flush();
//        writer.close();
//        os.close();
//
//        conn.connect();
    }
}
