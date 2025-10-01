package com.upc.cicloestrella.filters.auth;


import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.repositories.interfaces.application.auth.TokenRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.services.auth.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/auth")){
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(userEmail == null || authentication != null  ){
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        final boolean isTokenExpiredOrRevoked = tokenRepository.findByToken(jwt)
                .map(token -> !token.isExpired() && !token.isRevoked())
                .orElse(false);

        if(!isTokenExpiredOrRevoked) {
            filterChain.doFilter(request, response);
            return;
        }

        final Optional<User> user = userRepository.findByEmail(userEmail);

        if(user.isEmpty() || !jwtService.isTokenValid(jwt , user.get())) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
