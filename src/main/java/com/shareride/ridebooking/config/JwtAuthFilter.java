package com.shareride.ridebooking.config;

import com.shareride.ridebooking.service.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.shareride.ridebooking.utils.Constants.AUTHORIZATION;
import static com.shareride.ridebooking.utils.Constants.BEARER_;
import static com.shareride.ridebooking.utils.Constants.JWT_EXCEPTION;
import static com.shareride.ridebooking.utils.Constants.EMPTY;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader(AUTHORIZATION);
            String token = null;
            String username = null;

            if (authHeader != null && authHeader.startsWith(BEARER_)) {
                token = authHeader.substring(7);
                if (jwtService.validateToken(token)) {
                    username = jwtService.extractUsername(token);
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtService.extractAuthorities(token);
                UserDetails userDetails = new User(username, EMPTY, authorities);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtException | IllegalArgumentException ex) {
            request.setAttribute(JWT_EXCEPTION, ex);
        }

        filterChain.doFilter(request, response);
    }
}
