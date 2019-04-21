package ru.otus.l14.security;

import org.springframework.util.DigestUtils;

public class Crypt {

    public static String cryptWithMD5(String value) {
        return DigestUtils.md5DigestAsHex(value.getBytes());
    }
}
