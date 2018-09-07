package ru.rearitem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpClientHelper {//todo move to examples

    static Response sendPost(String urlStr, Map<String,String> params) throws Exception {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,String> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        try (OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream())) {
            out.write(postData.toString());
        }
        int respCode = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
        }
        printCookies(conn);
        return new Response(respCode,sb.toString());
    }

    static Response sendGet(String urlStr) throws Exception{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setDoInput(true);
        int respCode = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
        }
        return new Response(respCode,sb.toString());
    }

    static void printCookies(HttpURLConnection conn){
        java.net.CookieManager msCookieManager = new java.net.CookieManager();

        Map<String, List<String>> headerFields = conn.getHeaderFields();
        List<String> cookiesHeader = headerFields.get("Set-Cookie");

        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
        System.out.println("msCookieManager = " + msCookieManager.getCookieStore().getCookies());
    }

    static class Response{
        int code;
        String string;

        Response(int code, String string) {
            this.code = code;
            this.string = string;
        }
    }
}
