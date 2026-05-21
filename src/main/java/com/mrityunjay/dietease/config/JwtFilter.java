package com.mrityunjay.dietease.config;

import com.mrityunjay.dietease.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Look at the HTTP Header named "Authorization"
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // 2. Check if they brought a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Cut off the word "Bearer " to get the raw string
            try {
                email = jwtUtil.extractEmail(token); // Scan the token for the email
            } catch (Exception e) {
                System.out.println("Invalid or expired token!");
            }
        }

        // 3. If we found an email, and the user isn't already logged in for this specific request
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            if (jwtUtil.isTokenValid(token)) {
                // Read their role from the badge (e.g., "USER")
                String role = jwtUtil.extractRole(token);
                
                // Create the official Spring Security VIP Wristband
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Snap the wristband onto the user for the duration of this API call
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 4. Pass the request to the next guard in line (or to the Controller if they are done)
        filterChain.doFilter(request, response);
    }
}