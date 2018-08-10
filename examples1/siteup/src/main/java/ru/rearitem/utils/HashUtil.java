package ru.rearitem.utils;

import java.security.MessageDigest;
import java.util.Objects;

public class HashUtil {
    public static byte[] hash(byte[] psw) throws Exception{
        Objects.requireNonNull(psw);
        byte[] sault =
                { (byte) 0xA9, (byte) 0x8C, (byte) 0xE7, (byte) 0x42, (byte) 0xF6,
                        (byte) 0x46, (byte) 0xB3, (byte) 0x14 };
        byte[] result = new byte[sault.length + psw.length];
        System.arraycopy(sault, 0, result, 0, sault.length);
        System.arraycopy(psw, 0, result, sault.length, psw.length);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        result = digest.digest(result);
        result = digest.digest(result);//bdl hash
        return result;
    }
}
