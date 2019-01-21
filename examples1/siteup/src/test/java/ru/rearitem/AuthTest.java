package ru.rearitem;

import java.util.HashMap;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rearitem.httpclient.HttpResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
@Slf4j
public class AuthTest extends ServerTest {
    @Test
    public void shouldUserLoginAndLogout() throws Exception{
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
        assertThat(response.getCode(), is(401));
    }

    public static void main(String[] args) {
        //fibCiclicBuffer();
    }

    private static void fibCyclicBuffer() {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        if(n==1){
            System.out.println(1);
        }else {
            long[] arr = new long[10];
            arr[0] = 0;
            arr[1] = 1;
            int i = 2;
            int currPos = 0;
            for (; i <= n; i++) {
                currPos = i%10;
                int prev = (i-1+10)%10;
                int prevPrev = (i-2+10)%10;
                arr[currPos] = arr[prev] + arr[prevPrev];
                arr[currPos]%=m;
            }
            System.out.println(arr[currPos]);
        }
    }

    /**
     * Дано число 1≤n≤10^7, необходимо найти последнюю цифру n-го числа Фибоначчи.
     *
     */
    private static void fibMod10() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if(n==1){
            System.out.println(1);
        }else {
            long[] arr = new long[n+1];
            arr[0] = 0;
            arr[1] = 1;
            for (int i = 2; i <= n; i++) {
                arr[i] = arr[i - 1] + arr[i - 2];
                arr[i]%=10;
            }
            System.out.println(arr[n]);
        }
    }

    private static void fibN() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if(n==1){
            System.out.println(0);
        }else {
            int[] arr = new int[n+1];
            arr[0] = 0;
            arr[1] = 1;
            for (int i = 2; i <= n; i++) {
                arr[i] = arr[i - 1] + arr[i - 2];
            }
            System.out.println(arr[n]);
        }
    }
}
