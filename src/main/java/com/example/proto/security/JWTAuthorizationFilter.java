package com.example.proto.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import static com.example.proto.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // IF token exists try to verify and auth, otherwise exec next filter
        String token = req.getHeader(TOKEN_HEADER);

        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // Attempts to validate the user and authenticate
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null) {
            try {

                byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
                Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
                String username = parsedToken.getBody().getSubject();
                if (username != null) {
                    logger.warn(String.format("user recieved from token: { %s }", username));
                    return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                }
            } catch (JwtException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
