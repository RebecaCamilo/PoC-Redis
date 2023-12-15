package com.example.pocredis.config;

import lombok.SneakyThrows;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashKey implements KeyGenerator {

    @SneakyThrows
    @Override
    public Object generate(Object target, Method method, Object... params) {
        Object key = params[0];
        return generateHash((Long) key);
    }

    public static String generateHash(Long number) throws NoSuchAlgorithmException {
        String input = String.valueOf(number);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(input.getBytes());

        // Convert byte array to hexadecimal
        StringBuilder hashStringBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            hashStringBuilder.append(String.format("%02x", b));
        }

        return hashStringBuilder.toString();
    }
}
