package ru.rearitem.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    static final ThreadLocal<DateFormat> DATE_FORMAT =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd");
                }
            };

    public static <T> String toJson(T sdi) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.setDateFormat(DATE_FORMAT.get());
        return mapper.writeValueAsString(sdi);
    }

    public static <T> T fromJson(String json, Class<T> cls) throws Exception {
        if (json == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
//        mapper.setDateFormat(DATE_FORMAT.get());
        return mapper.readValue(json, cls);
    }
}
