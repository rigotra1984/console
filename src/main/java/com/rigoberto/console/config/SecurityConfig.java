package com.rigoberto.console.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    // https://docs.spring.io/spring-security/reference/6.1/servlet/integrations/cors.html#page-title
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedOrigins(Arrays.asList(this.allowedOrigins));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // https://docs.spring.io/spring-security/reference/reactive/oauth2/resource-server/jwt.html

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        // Elimina las líneas relacionadas con /api/event/**
                        .requestMatchers(HttpMethod.POST, "/api/transport/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.PUT, "/api/transport/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/transport/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/api/transport/**").hasAuthority("ROLE_user")

                        .requestMatchers(HttpMethod.POST, "/api/driver/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.PUT, "/api/driver/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/driver/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/api/driver/**").hasAuthority("ROLE_user")

                        .requestMatchers(HttpMethod.POST, "/api/passenger/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.PUT, "/api/passenger/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/passenger/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/api/passenger/**").hasAuthority("ROLE_user")

                        .requestMatchers("/api/event/**").permitAll()

                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()

                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter)))
                .cors(Customizer.withDefaults())
                .build();
    }

}