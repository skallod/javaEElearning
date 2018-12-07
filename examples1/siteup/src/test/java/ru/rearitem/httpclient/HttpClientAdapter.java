package ru.rearitem.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientAdapter {
    private static final Logger log = Logger.getLogger(HttpClientAdapter.class);
    final private CloseableHttpClient httpclient;
    final CookieStore httpCookieStore;

    public HttpClientAdapter() {
        RequestConfig globalConfig = RequestConfig.custom()
                .setSocketTimeout(5_000)
                .setConnectionRequestTimeout(5_000)
                .setConnectTimeout(5_000)
                .setCookieSpec(CookieSpecs.STANDARD).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(10);
        cm.setDefaultMaxPerRoute(10);
        httpCookieStore = new BasicCookieStore();
        httpclient =HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(httpCookieStore).build();
    }

    public HttpResponse makePostAndSend(String url, Map<String,String> formParams) throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String,String> param : formParams.entrySet()) {
            params.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params));
//        httppost.setHeader("Cache-Control", "no-cache");
//        httppost.setHeader("Connection", "keep-alive");
        return send(httppost);
    }

    public HttpResponse makeGetAndSend(String url) throws Exception {
        HttpGet httpget = new HttpGet(url);
        return send(httpget);
    }

    public HttpResponse send(HttpRequestBase req) throws Exception {
        Objects.requireNonNull(req);
        log.info("request " + req.getURI());
        long time = System.nanoTime();
        String respStr;
        int statusCode = 404;
        HttpEntity respentity = null;
        String reasonPhrase = null;
        HttpContext httpContext = new HttpClientContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, httpCookieStore);
        try (CloseableHttpResponse response = httpclient.execute(req,httpContext);) {
            StatusLine statusLine = response.getStatusLine();
            statusCode = statusLine.getStatusCode();
            reasonPhrase = statusLine.getReasonPhrase();
            respentity = response.getEntity();
            if (statusLine.getStatusCode() >= 300 || respentity == null) {
                return new HttpResponse(statusLine.getStatusCode(), null);
            }
            respStr = new String(EntityUtils.toByteArray(respentity)
                    , ContentType.getOrDefault(respentity).getCharset());
        } finally {
            log.info(String.format("http finish time=%d code=%d reason=%s"
                    ,TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time)
                    ,statusCode
                    ,reasonPhrase
            ));
        }
        List<Cookie> cookies = httpCookieStore.getCookies();
        log.info("cookies = " + cookies);
        return new HttpResponse(statusCode, respStr);
    }

    public void dispose() {
        try {
            httpclient.close();
        } catch (Exception e) {
            log.error("http client close", e);
        }
    }
}
