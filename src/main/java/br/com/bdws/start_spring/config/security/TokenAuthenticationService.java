package br.com.bdws.start_spring.config.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Service
public class TokenAuthenticationService {

    private static final long EXPIRATION_TIME = 3600000; // 1 hora
    private static final String SECRET = "MySecretKey";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserDetailsService userDetailsService;

    public void addAuthentication(HttpServletResponse response, String username) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (Objects.nonNull(token) && !token.isEmpty()) {
            String user = getUserFromToken(token);
            if (user != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user);
                Collection<? extends GrantedAuthority> authorities =
                        userDetails.getAuthorities();
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        }
        return null;
    }

    private String getUserFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}