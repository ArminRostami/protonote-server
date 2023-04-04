package com.example.proto.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.proto.models.AuthUser;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.proto.security.SecurityConstants.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// Extend spring security filter to add JWT logic
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(AUTH_LOGIN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        // Attempt authentication by sending username and password
        try {
            AuthUser user = new ObjectMapper().readValue(req.getInputStream(), AuthUser.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword());
            return this.authenticationManager.authenticate(authRequest);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        // Build JWT token and add it to response header
        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        byte[] signingKey = JWT_SECRET.getBytes();

        String token = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", TOKEN_TYPE).setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).compact();

        res.addHeader(TOKEN_HEADER, token);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");

    }

}