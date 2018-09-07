package ru.rearitem.httpclient;

import java.util.Optional;

public class HttpResponse {

    final private static int ERROR_CODE = 300;

    public final static HttpResponse ERROR = new HttpResponse(500 ,null);

    public final static HttpResponse OK = new HttpResponse(200 ,"");

    private int code;

    private String respString;

    public HttpResponse(int code, String respString) {
        this.code = code;
        this.respString = respString;
    }

    public int getCode() {
        return code;
    }

    public Optional<String> getRespString() {
        return respString == null ? Optional.empty() : Optional.of(respString);
    }

    public boolean isError() {
        return code >= ERROR_CODE;
    }

    public void setRespString(String respString) {
        this.respString = respString;
    }
}
