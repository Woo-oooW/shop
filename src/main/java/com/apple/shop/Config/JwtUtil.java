package com.apple.shop.Config;

import com.apple.shop.Member.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
            ));

    // JWT 만들어주는 함수
    public static String createToken(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        System.out.println("auth.getauthorities 출력");
        System.out.println(auth.getAuthorities());
        var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(","));
        System.out.println("getusername 출력");
        System.out.println(user.getUsername());
        String jwt = Jwts.builder()
                .claim("username", user.getUsername())
                .claim("displayName", user.displayName)
                .claim("authorities",user.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 10000)) //유효기간 10초
                .signWith(key)
                .compact();

        System.out.println("jwt생성결과");
        System.out.println(jwt);
        return jwt;
    }

    // JWT 까주는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();

        System.out.println("jwt깐결과");
        System.out.println(claims);
        return claims;
    }

}