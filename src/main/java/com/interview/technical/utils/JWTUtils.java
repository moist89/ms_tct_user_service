package com.interview.technical.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWTUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long expiration_millis = 3600_00;
    public static String generateToken(String name, String email) {
        return Jwts.builder()
                .claim("name",name)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration_millis))
                .signWith(key)
                .compact();
    }
}
