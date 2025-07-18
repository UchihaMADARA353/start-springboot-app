package com.example.start_spring_app.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class TokenEncryptor {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY = "0123456789abcdef"; // 16 bytes ➜ AES-128
    private static final String INIT_VECTOR = "abcdef9876543210"; // 16 bytes IV

    public static String encrypt(String plainText) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encrypted) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(original);
    }
}
