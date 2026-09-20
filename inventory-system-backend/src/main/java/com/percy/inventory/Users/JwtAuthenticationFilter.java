package com.percy.inventory.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final String secret;

    public JwtAuthenticationFilter(
            CustomUserDetailsService userDetailsService,
            @Value("${jwt.secret}") String secret
    ) {
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authorizationHeader.substring(7);

        try {
            SecretKey key =
                    io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                            secret.getBytes(StandardCharsets.UTF_8)
                    );

            Claims claims =
                    Jwts.parser()
                            .verifyWith(key)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();

            String username = claims.getSubject();

            if (username != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                Users user =
                        (Users) userDetailsService
                                .loadUserByUsername(username);

                System.out.println(
                        "JWT USER: " + user.getUsername()
                );

                System.out.println(
                        "JWT ROLE: " + user.getRole()
                );

                System.out.println(
                        "JWT AUTHORITIES: "
                                + user.getAuthorities()
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (Exception exception) {
            System.out.println(
                    "JWT authentication failed: "
                            + exception.getMessage()
            );
        }

        filterChain.doFilter(request, response);
    }
}