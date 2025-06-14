package com.albtoc.TableroTareas.infraestructure.security;

import com.albtoc.TableroTareas.infraestructure.adapter.JwtAdapter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class FilterJwt extends OncePerRequestFilter {
    @Autowired
    private JwtAdapter jwtAdapter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = jwtAdapter.getTokenOfRequest(request);
        if (token.isEmpty() || !jwtAdapter.isTokenValid(token.get())) {
            filterChain.doFilter(request, response);
            return;
        }

        String username;
        String authority;
        try {
            username = jwtAdapter.getUsernameOfToken(token.get());
            authority = jwtAdapter.getAuthorityOfToken(token.get());
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority(authority)));
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }
}
