package com.sba.learning.config;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;

public class GenerateBase64Key {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Base64-encoded secret key:");
        System.out.println(base64Key);
    }
}
