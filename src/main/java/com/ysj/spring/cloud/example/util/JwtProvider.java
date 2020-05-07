package com.ysj.spring.cloud.example.util;

import com.ysj.spring.cloud.example.domain.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtProvider {
    private static final String SIGNING_KEY = "test-security-key";
    private static final String AUTHORITIES_KEY = "scopes";
    private static long ACCESS_TOKEN_EXPIRE_TIME = 60;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Account account) {
        return doGenerateToken(account.getUsername());
    }

    private String doGenerateToken(String subject) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put(AUTHORITIES_KEY, Arrays.asList("ROLE_ADMIN"));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://devglan.com")
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME * 1000))
                .compact();
    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

}
