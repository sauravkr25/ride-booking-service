package com.shareride.ridebooking.service.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.shareride.ridebooking.utils.Constants.Properties.JWT_SECRET_KEY;
import static com.shareride.ridebooking.utils.Constants.ROLES;

@Service
public class JwtService {

    private final String jwtSecret;

    public JwtService(@Value(JWT_SECRET_KEY) String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token); // Will throw exception if invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw e;
        }
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = parseClaims(token);
        List<String> roles = claims.get(ROLES, List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
