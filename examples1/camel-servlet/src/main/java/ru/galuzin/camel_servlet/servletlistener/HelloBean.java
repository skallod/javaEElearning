package ru.galuzin.camel_servlet.servletlistener;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.http.common.HttpMessage;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.language.Simple;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

public class HelloBean {

//    public String sayHello(@Header("name") String name, @Simple("${sys.user.country}") String country, @Body InputStream body) {
//        System.out.println("body = " + body);
//        return "Hello " + name + ", how are you? You are from: " + country;
//    }

    public String process(Exchange exchange) throws Exception {
        HttpMessage in = exchange.getIn(HttpMessage.class);
        in.getResponse().setStatus(203);
        return "success";
    }
}
