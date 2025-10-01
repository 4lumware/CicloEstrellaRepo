package com.upc.cicloestrella.configs.auth;


import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AppConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));

            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name()))
                    .collect(Collectors.toUnmodifiableList());

            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();
        };
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
