package com.andi.mycourses.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author andi
 */
public class JwtUtil {
    @Value("${mycourses.expiration_time}")
    long expiration_time;
    @Value("${mycourses.token_prefix}")
    String token_prefix;
    @Value("${mycourses.secret}")
    String secret;
    @Value("${mycourses.key_prefix}")
    String key_prefix;

    public String generateToken(String email) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);

        String jwt = token_prefix + Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + expiration_time))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
//        template.opsForValue().append(key_prefix + user.getUserName(), jwt);

        return  jwt;
    }

    public Map<String, Object> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace(token_prefix, ""))
                .getBody();
    }
}
