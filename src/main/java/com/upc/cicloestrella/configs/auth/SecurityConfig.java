package com.upc.cicloestrella.configs.auth;


import com.upc.cicloestrella.exceptions.auth.CustomAccessDeniedHandler;
import com.upc.cicloestrella.exceptions.auth.CustomAuthenticationEntryPoint;
import com.upc.cicloestrella.filters.auth.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers("/auth/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/campuses/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/careers/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/courses/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET, "/formats/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/formalities/**" ).permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/teachers/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/students/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/reactions/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/reviews/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET , "/reactions/**").permitAll();
                            // Permitir acceso público a imágenes estáticas (cubre /images/profiles/...)
                            authorize.requestMatchers("/images/**", "/static/**").permitAll();
                            authorize.anyRequest().authenticated();
                        }
                ).exceptionHandling(exception -> {
                    exception
                            .authenticationEntryPoint(customAuthenticationEntryPoint) // 401
                            .accessDeniedHandler(customAccessDeniedHandler); // 403
                })
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Ignorar completamente estas rutas para que Spring Security no intervenga
        return web -> web.ignoring().requestMatchers("/images/**", "/static/**", "/favicon.ico");
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
