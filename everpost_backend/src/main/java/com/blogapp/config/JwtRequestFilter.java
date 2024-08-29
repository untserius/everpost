package com.blogapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secretkey}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {

            try {
                String extractedToken = requestHeader.replace("Bearer ", "");

                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(extractedToken);

                Claims body = claimsJws.getBody();

                String username = body.getSubject();

                List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        simpleGrantedAuthorities
                );

                SecurityContextHolder
                        .getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw e;
            }
            filterChain.doFilter(request, response);
        }
    }
}
