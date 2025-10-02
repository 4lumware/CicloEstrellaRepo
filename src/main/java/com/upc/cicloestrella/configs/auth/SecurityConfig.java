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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
}
