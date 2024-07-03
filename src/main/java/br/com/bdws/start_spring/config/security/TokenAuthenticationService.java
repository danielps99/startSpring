package br.com.bdws.start_spring.config.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationService {

    private static final long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(1);
    private static final String SECRET = "638CBE3A90E0303BF3808F40F95A7F02A24B4B5D029C954CF553F79E9EF1DC0384BE681C249F1223F6B55AA21DC070914834CA22C8DD98E14A872CA010091ACC";
    private static final String HEADER_STRING = "Authorization";

    private final UserDetailsService userDetailsService;

    public void addAuthentication(HttpServletResponse response, String username) {
        Map<String, String> claims = new HashMap<>();
        claims.put("sit", "https://secure.bdws.com.br");
        String jwt = Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                .signWith(generateKey())
                .compact();

        response.addHeader(HEADER_STRING, jwt);
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        try {
            String user = getUserFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user);
            Collection<? extends GrantedAuthority> authorities =
                    userDetails.getAuthorities();
            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        } catch (JwtException | NullPointerException | StringIndexOutOfBoundsException e) {
            return null;
        }
    }

    private String getUserFromToken(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token.substring(7))
                .getPayload()
                .getSubject();
    }
}