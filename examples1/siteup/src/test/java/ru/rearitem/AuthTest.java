package ru.rearitem;

import java.util.HashMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rearitem.httpclient.HttpResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AuthTest extends ServerTest {
    private static final Logger log = LoggerFactory.getLogger(AuthTest.class);
    @Test
    public void test1() throws Exception{
        HttpResponse response = client.makePostAndSend("http://localhost:58088/api/account",
                new HashMap<String, String>() {{
                    put("email", "test");
                    put("password", "test");
                    put("name", "test");
                }});
        log.info("response.getString = " + response.getRespString());
        assertThat(response.getCode(), is(200));
        response = client.makePostAndSend("http://localhost:58088/api/login",
                new HashMap<String, String>() {{
                    put("email", "test");
                    put("password", "test");
                }});
        assertThat(response.getCode(), is(200));
        response = client.makeGetAndSend("http://localhost:58088/api/user/lk");
        assertThat(response.getCode(), is(200));
        response = client.makeGetAndSend("http://localhost:58088/api/admin/lk");
        assertThat(response.getCode(), is(403));
        response = client.makeGetAndSend("http://localhost:58088/api/logout");
        assertThat(response.getCode(), is(200));
        response = client.makeGetAndSend("http://localhost:58088/api/user/lk");
        assertThat(response.getCode(), is(403));
    }
}
